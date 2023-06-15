package com.springboot.hello.data.entity.listener;

import com.springboot.hello.data.entity.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

//insert, update시 prePersist, preUpdate 통해 날짜값 갱신 가능
public class CustomListener {

    private final Logger LOGGER = LoggerFactory.getLogger(CustomListener.class);

    @PostLoad //select쿼리 발생 이후 postLoad
    public void postLoad(Listener listener) {
        LOGGER.info("[postLaod] called");
    }

    @PrePersist //insert 쿼리 발생 이전 prePersist
    public void prePersist(Listener listener) {
        LOGGER.info("[prePersist] called");
    }

    @PostPersist //insert 쿼리 발생 이후 postPersist
    public void postPersist(Listener listener) {
        LOGGER.info("[postPersist] called");
    }

    @PreUpdate //update 쿼리 발생 이전 preUpdate
    public void preUpdate(Listener listener) {
        LOGGER.info("[preUpdate] called");
    }

    @PreRemove //delete 쿼리 발생 이전 preRemove
    public void preRemove(Listener listener) {
        LOGGER.info("[preRemove] called");
    }

    @PostRemove //delete 쿼리 발생 이후 preRemove
    public void postRemove(Listener listener) {
        LOGGER.info("[postRemove] called");
    }


}
