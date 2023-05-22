package com.springboot.hello.controller;

import com.springboot.hello.dto.MemberDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/put-api")
public class PutController {

    //RequestBody를 활용한 PUT 메서드
    @PutMapping(value = "/member")
    public String postMember(@RequestBody Map<String, Object> putData) {
        StringBuilder sb = new StringBuilder();

        putData.entrySet().forEach(map-> {
            sb.append(map.getKey() + " : " + map.getValue() + "\n");
        });

        return sb.toString();
    }

    //DTO객체를 활용한 PUT 메서드
    @PutMapping(value = "/member1")
    public String postMemberDTO1(@RequestBody MemberDTO memberDTO) {
        return memberDTO.toString();
    }

    //JSON형식으로 전달
    @PutMapping(value = "/member2")
    public MemberDTO postMemberDTO2(@RequestBody MemberDTO memberDTO) {
        return memberDTO;
    }

    //ResponseEntity를 활용한 PUT 메서드(헤더와 바디 구성)
    @PutMapping(value = "/member3")
    public ResponseEntity<MemberDTO> postMemberDTO3(@RequestBody MemberDTO memberDTO) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memberDTO);
    }
}
