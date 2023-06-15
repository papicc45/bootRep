package com.springboot.hello.service;

import com.springboot.hello.data.entity.Listener;

public interface ListenerService {
    Listener getEntity(Long id);

    void saveEntity(Listener listener);

    void updateEntity(Listener listener);

    void removeEntity(Listener listener);
}
