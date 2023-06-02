package com.springboot.hello.service;

import com.springboot.hello.dto.MemberDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebClientService {

    public String getName() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        return webClient.get().uri("/api/v1/crud-api").retrieve().bodyToMono(String.class).block();
    }

    public String getNameWithPathVariable() {
        WebClient webClient = WebClient.create("http://localhost:9090");

        ResponseEntity<String> responseEntity = webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/{name}").build())
                .retrieve().toEntity(String.class).block();

        return responseEntity.getBody();
    }

    public String getNameWithParameter() {
        WebClient webClient = WebClient.create("http://localhost:9090");

        return webClient.get().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api").queryParam("name", "flature").build())
                .exchangeToMono(clientResponse -> {
                    if(clientResponse.statusCode().equals(HttpStatus.OK)) {
                        return clientResponse.bodyToMono(String.class);
                    } else {
                        return clientResponse.createException().flatMap(Mono::error);
                    }
                }).block();
    }

    public ResponseEntity<MemberDTO> postWithParamAndBody() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDTO memberDTO = MemberDTO.builder().name("flature").email("papicc45@naver.com").organization("around").build();

        return webClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api")
                .queryParam("name", "flature")
                .queryParam("email", "jerneithe@nate.com")
                .queryParam("organization", "wiki").build())
                .bodyValue(memberDTO)
                .retrieve().toEntity(MemberDTO.class).block();
    }

    public ResponseEntity<MemberDTO> postWithHeader() {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:9090")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();

        MemberDTO memberDTO = MemberDTO.builder().name("flature").email("papicc45@naver.com").organization("around").build();

        return webClient.post().uri(uriBuilder -> uriBuilder.path("/api/v1/crud-api/add-header").build())
                .bodyValue(memberDTO)
                .header("my-header", "api").retrieve().toEntity(MemberDTO.class).block();
    }
}
