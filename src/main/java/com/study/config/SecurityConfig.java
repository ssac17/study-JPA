package com.study.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
/*
*@EnableWebSecurity
* 웹 보안 기능을 활성화하려는 클래스에 추가
* 보통 @Configuration 같이 사용
* */
@EnableWebSecurity
public class SecurityConfig {

    /**
     * SecurityFilterChain
     * WebSecurityConfigurerAdapter deprecated 되어 SecurityFilterChain 사용
     * antMatchers(), mvcMatchers(), regexMatchers() -> requestMatchers() (또는 securityMatchers())로 변경
     * authorizeRequests() -> authorizeHttpRequests() 로 변경
     * 람다식으로 변경
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
             //authorizeHttpRequests: HTTP 요청에 대한 접근 권한을 설정하는 데 사용
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/", "/login","/sign-up","/checked-email",
                "/check-email-token","/email-login","/check-email-login","/login-link", "/node_modules/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/profile/").permitAll()
                .anyRequest().authenticated()
        );
        //csrf처리
        http.csrf((csrf) -> csrf.disable());

        http.securityContext((securityContext) -> securityContext.requireExplicitSave(false));

        return http.build();
    }

    /**
     * WebSecurityConfigurerAdapter deprecated 되어 SecurityFilterChain 사용
     * WebSecurity -> WebSecurityCustomizer로 대체
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
                        //static리소스는 접근 허용(무시)
        return web -> web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
