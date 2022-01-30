package com.grzegorz.rychlik.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.grzegorz.rychlik.backend.model.dto.ErrorDto;
import com.grzegorz.rychlik.backend.model.dto.LoginDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            LoginDto loginDto = objectMapper.readValue(request.getInputStream(), LoginDto.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);
        } catch (IOException e) {
            throw new AuthenticationServiceException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Claims claims = new DefaultClaims()
                .setSubject(authResult.getName())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        claims.put("authorities",authResult.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(",")));
        String token = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, "Sekretny Klucz").compact();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), Collections.singletonMap("token",token));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        if (failed instanceof LockedException) {
            response.setStatus(401);
            objectMapper.writeValue(response.getWriter(),new ErrorDto("Aktywuj swoje konto"));
            return;
        } else if(failed instanceof BadCredentialsException){
            response.setStatus(401);
            objectMapper.writeValue(response.getWriter(),new ErrorDto("Blendne dane do logowania"));
            return;
        }
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
