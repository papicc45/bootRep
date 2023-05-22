package com.springboot.hello.controller;

import com.springboot.hello.dto.MemberDTO;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/post-api")
public class PostController {

    //RequestMapping으로 POST 메서드 구현
    @RequestMapping(value = "/domain", method = RequestMethod.POST)
    public String postExample() {
        return "Hello Post API";
    }

    //RequestBody와 Map을 활용한 POST API 구현
    @PostMapping(value = "/member")
    public String postMember(@RequestBody Map<String, String> postData) {
        StringBuilder sb = new StringBuilder();

        postData.entrySet().forEach(map-> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });

        return sb.toString();
    }

    //DTO객체를 활용한 POST API 구현
    @PostMapping(value = "/member2")
    public String postMemberDTO(MemberDTO memberDTO) {
        return memberDTO.toString();
    }
}
