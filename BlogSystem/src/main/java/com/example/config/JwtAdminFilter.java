package com.example.config;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAdminFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorHeader = request.getHeader(AUTHORIZATION);
		
		String token =authorHeader;
		Claims claims = Jwts.parser().setSigningKey("MySecret").parseClaimsJws(token).getBody();

		
		filterChain.doFilter(request, response);
	}

}
