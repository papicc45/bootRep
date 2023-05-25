package com.springboot.hello.controller;

import com.springboot.hello.data.group.ValidationGroup1;
import com.springboot.hello.data.group.ValidationGroup2;
import com.springboot.hello.dto.ValidRequestDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/validation")
public class ValidationController {

    private final Logger LOGGER = LoggerFactory.getLogger(ValidationController.class);

    @PostMapping("/valid")
    public ResponseEntity<String> checkValidationByValid(@Valid @RequestBody ValidRequestDTO validRequestDTO) {
        LOGGER.info(validRequestDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(validRequestDTO.toString());
    }

    @PostMapping("/validated")
    public ResponseEntity<String> checkValidation(@Validated @RequestBody ValidRequestDTO validRequestDTO) {
        LOGGER.info(validRequestDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(validRequestDTO.toString());
    }

    @PostMapping("/validated/group1")
    public ResponseEntity<String> checkValidation1(@Validated(ValidationGroup1.class) @RequestBody ValidRequestDTO validRequestDTO) {
        LOGGER.info(validRequestDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(validRequestDTO.toString());
    }

    @PostMapping("/validated/group2")
    public ResponseEntity<String> checkValidation2(@Validated(ValidationGroup2.class) @RequestBody ValidRequestDTO validRequestDTO) {
        LOGGER.info(validRequestDTO.toString());
        return ResponseEntity.status(HttpStatus.OK).body(validRequestDTO.toString());
    }

}
