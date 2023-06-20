package com.springboot.hello.data.repository;

import com.springboot.hello.dto.ShortUrlResponseDTO;
import org.springframework.data.repository.CrudRepository;

public interface ShortUrlRedisRepository extends CrudRepository<ShortUrlResponseDTO, String> {
}
