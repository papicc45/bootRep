package com.springboot.hello.controller;

import com.springboot.hello.dto.ShortUrlResponseDTO;
import com.springboot.hello.service.ShortUrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/short-url")
public class ShortUrlController {

    private final Logger LOGGER = LoggerFactory.getLogger(ShortUrlController.class);

    @Value("${prac.short.url.id}")
    private String CLIENT_ID;

    @Value("${prac.short.url.secret}")
    private String CLIENT_SECRET;

    private final ShortUrlService shortUrlService;

    @Autowired
    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostMapping()
    public ShortUrlResponseDTO generateShortUrl(String originUrl) {
        LOGGER.info("[generateShortUrl] perform API, CLIENT_ID : {}, CLIENT_SECRET : {}", CLIENT_ID, CLIENT_SECRET);
        return shortUrlService.generateShortUrl(CLIENT_ID, CLIENT_SECRET, originUrl);
    }

    @GetMapping()
    public ShortUrlResponseDTO getShortUrl(String originUrl) {
        ShortUrlResponseDTO shortUrlResponseDTO = new ShortUrlResponseDTO("ss", "ss");
        return shortUrlService.getShortUrl(CLIENT_ID, CLIENT_SECRET, originUrl);
    }
}
