package com.sangkwon.backend.domain.auth.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sangkwon.backend.domain.auth.dao.AuthDAO;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtTokenProvider jwtTokenProvider;
    private final AuthDAO authDAO;

    // 생성자 주입
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, AuthDAO authDAO) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authDAO = authDAO;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Authorization 헤더에서 "Bearer " 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰이 존재하고 유효하면
        if (token != null && jwtTokenProvider.validateToken(token)) {

            // 3. 블랙리스트에 등록된 토큰인지 확인
            if (authDAO.isTokenBlacklisted(token)) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("블랙리스트에 등록된 토큰입니다.");
                return;
            }

            // 4. 사용자 이메일 추출
            String userEmail = jwtTokenProvider.getUserEmailFromToken(token);
            
            var authorities = java.util.List.of(new SimpleGrantedAuthority("ROLE_USER"));
            
            // 5. 사용자 인증 객체 생성 (권한 없음으로 설정)
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userEmail, null, authorities);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 6. SecurityContext에 등록 (이제 인증된 사용자로 간주됨)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // 다음 필터로 계속 진행
        filterChain.doFilter(request, response);
    }

    // Authorization 헤더에서 "Bearer " 접두사 제거 후 토큰만 추출
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // "Bearer " 이후 문자열 반환
        }
        return null;
    }
}
