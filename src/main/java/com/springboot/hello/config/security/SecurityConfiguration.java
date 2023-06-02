package com.springboot.hello.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public SecurityConfiguration(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic().disable()          //UI를 사용하는 것을 기본값으로 가진 시큐리티 설정을 비활성화
                .csrf().disable()                              //CSRF 비활성화
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     //REST API기반 애플리케이션 동작방식 설정, 현 프로젝트는 JWT 토큰인증, 세션 사용하지 않음
                .and()
                .authorizeRequests()                //애플리케이션에 들어오는 요청에 대한 사용권한 체크
                .antMatchers("/sign-api/sign-in", "/sign-api/sign-up", "/sign-api/exception").permitAll()
                .antMatchers(HttpMethod.GET, "/product/**").permitAll()
                .antMatchers("**exception**").permitAll()                   //위 경로에 대해서는 모두 허용
                .anyRequest().hasRole("ADMIN")                  //기타 요청은 인증권한을 가진 사용자에게 허용
                .and()
                .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())               //권환 확인과정에 통과하지 못하는 예외 처리
                .and()
                .exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint())         //인증과정에서 예외 처리
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
    }

    /**
     *  WebSecurity는 HttpSecurity 앞단에 적용되며, 스프링 시큐리티 영향권 밖에 있음
     *  인증/인가 적용되기 전에  동작하는 설정 -> 인증/인가 적용되지 않는 리소스 접근에 대해서만 사용
     */
    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring().antMatchers("/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/swagger/**", "/sign-api/exception");
        //swagger에 적용되는 인증/인가 피하기 위해 ignoring() 메서드 사용
    }
}
