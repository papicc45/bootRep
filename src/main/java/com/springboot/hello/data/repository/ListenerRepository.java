package com.springboot.hello.data.repository;

import com.springboot.hello.data.entity.Listener;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListenerRepository extends JpaRepository<Listener, Long> {
}
