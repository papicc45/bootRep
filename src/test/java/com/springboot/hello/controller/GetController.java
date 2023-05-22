package com.springboot.hello.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        return "Hello World";
    }

    //매개변수가 없는 GET 메서드
    @GetMapping(value = "/name")
    public String getName() {
        return "Flature";
    }

    //PathVariable을 활용한 매개변수를 가지는 GET 메서드
    @GetMapping(value = "/variable1/{variable}")
    public String getVariable1(@PathVariable String variable) {
        return variable;
    }

}
