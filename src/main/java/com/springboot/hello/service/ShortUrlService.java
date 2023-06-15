package com.springboot.hello.service;

import com.springboot.hello.dto.ShortUrlResponseDTO;

public interface ShortUrlService {

    ShortUrlResponseDTO getShortUrl(String clientId, String clientSecret, String originUrl);

    ShortUrlResponseDTO generateShortUrl(String clientId, String clientSecret, String originUrl);

    ShortUrlResponseDTO updateShortUrl(String clientId, String clientSecret, String originUrl);

    ShortUrlResponseDTO deleteByShortUrl(String shortUrl);

    ShortUrlResponseDTO deleteByOriginUrl(String originUrl);
}
