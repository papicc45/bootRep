package com.springboot.hello.data.dao.impl;

import com.springboot.hello.data.dao.ShortUrlDAO;
import com.springboot.hello.data.entity.ShortUrl;
import com.springboot.hello.data.repository.ShortUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShortUrlDAOImpl implements ShortUrlDAO {

    private final ShortUrlRepository shortUrlRepository;

    @Autowired
    public ShortUrlDAOImpl(ShortUrlRepository shortUrlRepository) {
        this.shortUrlRepository = shortUrlRepository;
    }

    @Override
    public ShortUrl saveShortUrl(ShortUrl shortUrl) {
        ShortUrl foundShortUrl = shortUrlRepository.save(shortUrl);
        return foundShortUrl;
    }

    @Override
    public ShortUrl getShortUrl(String originalUrl) {
        ShortUrl foundShortUrl = shortUrlRepository.findByOrOrgUrl(originalUrl);
        return foundShortUrl;
    }

    @Override
    public ShortUrl getOriginalUrl(String shortUrl) {
        ShortUrl foundShortUrl = shortUrlRepository.findByUrl(shortUrl);
        return null;
    }

    @Override
    public ShortUrl updateShortUrl(ShortUrl newShortUrl) {
        return null;
    }

    @Override
    public void deleteByShortUrl(String shortUrl) {

    }

    @Override
    public void deleteByOriginalUrl(String originalUrl) {

    }
}
