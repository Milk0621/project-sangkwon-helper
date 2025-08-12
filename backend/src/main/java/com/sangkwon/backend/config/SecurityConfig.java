package com.sangkwon.backend.config;
import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;
import com.sangkwon.backend.domain.auth.jwt.JwtAuthenticationFilter;
import com.sangkwon.backend.domain.auth.jwt.JwtTokenProvider;

@Configuration
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthDAO authDAO;

    SecurityConfig(JwtTokenProvider jwtTokenProvider, AuthDAO authDAO) {
        this.jwtTokenProvider = jwtTokenProvider;
		this.authDAO = authDAO;
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
			.cors(withDefaults())
		    .csrf(csrf -> csrf.disable()) // CSRF 비활성화
		    .authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/auth/**").permitAll() // 인증 없이 허용할 엔드포인트
				.requestMatchers("/api/users/me").authenticated()
				.requestMatchers("/api/adongs/search").authenticated()
				.anyRequest().authenticated()
		    )
		    .addFilterBefore(
	            new JwtAuthenticationFilter(jwtTokenProvider, authDAO), // 만든 필터 등록
	            UsernamePasswordAuthenticationFilter.class
	        )
	        .build();
    }
	
}
