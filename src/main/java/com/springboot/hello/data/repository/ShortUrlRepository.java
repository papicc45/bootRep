package com.springboot.hello.data.repository;


import com.springboot.hello.data.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

    ShortUrl findByUrl(String url);

    ShortUrl findByOrOrgUrl(String originUrl);
}
