package com.springboot.hello.controller;

import com.springboot.hello.data.entity.Listener;
import com.springboot.hello.service.ListenerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listener")
public class ListenerController {

    private ListenerService listenerService;

    public ListenerController(ListenerService listenerService) {
        this.listenerService = listenerService;
    }

    @GetMapping
    public String getListener(Long id) {
        listenerService.getEntity(id);

        return "OK";
    }

    @PostMapping
    public void saveListener(String name) {
        Listener listener = new Listener();
        listener.setName(name);

        listenerService.saveEntity(listener);
    }

    @PutMapping
    public void updateListener(Long id, String name) {
        Listener listener = Listener.builder().id(id).name(name).build();

        listenerService.updateEntity(listener);
    }

    @DeleteMapping
    public void deleteListener(Long id) {
        Listener listener = listenerService.getEntity(id);
        listenerService.removeEntity(listener);
    }
}
