package com.springboot.hello.controller;


import com.springboot.hello.dto.MemberDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/get-api")
public class GetController {

    private final Logger LOGGER = LoggerFactory.getLogger(GetController.class);

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        LOGGER.info("getHello 메서드가 호출됨");
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
        LOGGER.info("들어온 값 : {} ", variable);
        return variable;
    }

    //PathVariable 변수명 매핑
    @GetMapping(value = "/variable2/{variable}")
    public String getVariable2(@PathVariable("variable") String variable) {
        return variable;
    }

    //RequestParam을 활용한 GET 메서드, Swagger 명세 추가
    @GetMapping(value = "/request1")
    @ApiOperation(value = "GET 메서드 예제", notes = "RequestParam을 활용한 GET 메서드")
    public String getRequestParam1(@ApiParam(value = "이름", required = false) @RequestParam String name,
                                                        @ApiParam(value = "메일", required = false) @RequestParam String email,
                                                        @ApiParam(value = "회사", required = false) @RequestParam String organization) {

        return name + " " + email + " " + organization;
    }

    //RequestParam과 Map을 조합한 GET 메서드(쿼리스트링에 어떤 값이 들어올 지 모를때)
    @GetMapping(value = "/request2")
    public String getRequestParam2(@RequestParam Map<String, String> param) {
        StringBuilder sb = new StringBuilder();

        param.entrySet().forEach(map-> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });

        return sb.toString();
    }

    //DTO 객체를 활용한 GET 메서드
    @GetMapping(value = "/request3")
    public String getRequestParam3(MemberDTO memberDTO) {
        return memberDTO.toString();
    }
}
