package com.springboot.hello.data.dao;

import com.springboot.hello.data.entity.ShortUrl;
import com.springboot.hello.data.entity.ShortUrl;

public interface ShortUrlDAO {
    ShortUrl saveShortUrl(ShortUrl shortUrl);

    ShortUrl getShortUrl(String originalUrl);

    ShortUrl getOriginalUrl(String shortUrl);

    ShortUrl updateShortUrl(ShortUrl newShortUrl);

    void deleteByShortUrl(String shortUrl);

    void deleteByOriginalUrl(String originalUrl);


}
