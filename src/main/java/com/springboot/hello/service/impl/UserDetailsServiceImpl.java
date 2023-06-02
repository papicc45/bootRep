package com.springboot.hello.service.impl;

import com.springboot.hello.data.repository.UserRepository;
import com.springboot.hello.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUserName(String userName) throws UsernameNotFoundException {
        LOGGER.info("[loadUserByUserName] loadUserByUserName 수행. username : {}", userName);
        return userRepository.getByUid(userName);
    }
}
