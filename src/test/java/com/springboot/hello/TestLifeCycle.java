package com.springboot.hello;

import org.junit.jupiter.api.*;

public class TestLifeCycle {

    /***
     *  beforeAll, afterAll - 전체 테스트 동작에서 처음과 마지막에만 수행
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("## BeforeAll Annotation 호출 ##");
        System.out.println();
    }

    @AfterAll
    static void afterAll() {
        System.out.println("## AfterAll Annotation 호출 ##");
        System.out.println();
    }

    /***
     *  beforeEach afterEach - 각 테스트가 실행될 때 @Test 어노테이션이 지정된 테스트 메서드 기준으로 실행
     */
    @BeforeEach
    void beforeEach() {
        System.out.println("## beforeEach Annotation 호출 ##");
        System.out.println();
    }

    @AfterEach
    void afterEach() {
        System.out.println("## afterEach Annotation 호출 ##");
        System.out.println();
    }

    @Test
    void test1() {
        System.out.println("## test1 시작 ##");
        System.out.println();
    }

    @Test
    @DisplayName("Test Case 2 !!!")
    void test2() {
        System.out.println("## test2 시작 ##");
        System.out.println();
    }

    /**
     * @Disabled - 테스트 하지 않음, 테스트 메서드로는 인식되고 있어 비활성화 로그 출력
     */
    @Test
    @Disabled
    void test3() {
        System.out.println("## test3 시작 ##");
        System.out.println();
    }
}
