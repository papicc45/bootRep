package com.springboot.hello.service.impl;

import com.springboot.hello.data.dao.ShortUrlDAO;
import com.springboot.hello.data.entity.ShortUrl;
import com.springboot.hello.data.repository.ShortUrlRedisRepository;
import com.springboot.hello.data.repository.ShortUrlRepository;
import com.springboot.hello.dto.NaverUriDTO;
import com.springboot.hello.dto.ShortUrlResponseDTO;
import com.springboot.hello.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlServiceImpl.class);

    private final ShortUrlDAO shortUrlDAO;
    private final ShortUrlRedisRepository shortUrlRedisRepository;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlDAO shortUrlDAO, ShortUrlRedisRepository shortUrlRedisRepository) {
        this.shortUrlDAO = shortUrlDAO;
        this.shortUrlRedisRepository = shortUrlRedisRepository;
    }
    @Override
    public ShortUrlResponseDTO getShortUrl(String clientId, String clientSecret, String originUrl) {
        LOGGER.info("[getShortUrl] request data : {}", originUrl);

        //cache
        Optional<ShortUrlResponseDTO> foundResponseDTO = shortUrlRedisRepository.findById(originUrl);
        if(foundResponseDTO.isPresent()) {
            LOGGER.info("[getShortUrl] Cache Data is existed");
            return foundResponseDTO.get();
        } else {
            LOGGER.info("[getShortUrl] Cache Data does not existed");
        }
        ShortUrl getShortUrlEntity = shortUrlDAO.getShortUrl(originUrl);

        String orgUrl;
        String shortUrl;

        if(getShortUrlEntity == null) {
            LOGGER.info("[getShortUrl] No Entity in Database. ");
            ResponseEntity<NaverUriDTO> responseEntity = requestShortUrl(clientId, clientSecret, originUrl);

            orgUrl = responseEntity.getBody().getResult().getOrgUrl();
            shortUrl = responseEntity.getBody().getResult().getUrl();
            String hash = responseEntity.getBody().getResult().getHash();

            ShortUrl shortUrlEntity = new ShortUrl();
            shortUrlEntity.setOrgUrl(orgUrl);
            shortUrlEntity.setUrl(shortUrl);
            shortUrlEntity.setHash(hash);

            shortUrlDAO.saveShortUrl(shortUrlEntity);
        } else {
            orgUrl = getShortUrlEntity.getOrgUrl();
            shortUrl = getShortUrlEntity.getUrl();
        }

        ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO(orgUrl, shortUrl);
        shortUrlRedisRepository.save(shortUrlResponseDTO);

        LOGGER.info("[getShortUrl] Response DTO : {}", shortUrlResponseDTO);
        return shortUrlResponseDTO;
    }

    @Override
    public ShortUrlResponseDTO generateShortUrl(String clientId, String clientSecret, String originUrl) {
        LOGGER.info("[generateShortUrl] request data : {}", originUrl);

        ResponseEntity<NaverUriDTO> responseEntity = requestShortUrl(clientId, clientSecret, originUrl);

        String orgUrl = responseEntity.getBody().getResult().getOrgUrl();
        String shortUrl = responseEntity.getBody().getResult().getUrl();
        String hash = responseEntity.getBody().getResult().getHash();

        ShortUrl shortUrlEntity = ShortUrl.builder().orgUrl(orgUrl).url(shortUrl).hash(hash).build();

        shortUrlDAO.saveShortUrl(shortUrlEntity);

        ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO(orgUrl, shortUrl);

        //cache
        shortUrlRedisRepository.save(shortUrlResponseDTO);

        LOGGER.info("[generateShortUrl] Response DTO : {}", shortUrlResponseDTO.toString());
        return shortUrlResponseDTO;
    }

    private ResponseEntity<NaverUriDTO> requestShortUrl(String clientId, String clientSecret, String originUrl) {
        LOGGER.info("[requestShortUrl] client ID : ***, client Secret : ***, origin URL : {}", originUrl);

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/util/shorturl")
                .queryParam("url", originUrl)
                .encode()
                .build()
                .toUri();

        LOGGER.info("[requestShortUrl] set HTTP Request Header");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(new MediaType[]{MediaType.APPLICATION_JSON}));
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();

        LOGGER.info("[requestShortUrl] request By restTemplate");
        ResponseEntity<NaverUriDTO> responseEntity = restTemplate.exchange(uri, HttpMethod.GET, entity, NaverUriDTO.class);

        LOGGER.info("[requestShortUrl] request has been successfully complete.");

        return responseEntity;
    }

    @Override
    public ShortUrlResponseDTO updateShortUrl(String clientId, String clientSecret, String originUrl) {
        return null;
    }

    @Override
    public ShortUrlResponseDTO deleteByShortUrl(String shortUrl) {
        return null;
    }

    @Override
    public ShortUrlResponseDTO deleteByOriginUrl(String originUrl) {
        return null;
    }
}
