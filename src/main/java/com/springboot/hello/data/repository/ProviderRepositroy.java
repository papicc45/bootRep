package com.springboot.hello.data.repository;

import com.springboot.hello.data.entity.Provider;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProviderRepositroy extends JpaRepository<Provider, Long> {
}
