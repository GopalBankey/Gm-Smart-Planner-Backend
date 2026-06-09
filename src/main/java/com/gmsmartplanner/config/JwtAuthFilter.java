package com.gmsmartplanner.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter
        extends OncePerRequestFilter {

    @Value("${jwt.secret}")
    private String secretKey;

    private Key getSignKey() {

        byte[] keyBytes =
                Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader =
                request.getHeader(
                        "Authorization"
                );

        // NO TOKEN
        if (authHeader == null
                || !authHeader.startsWith(
                "Bearer "
        )) {

            filterChain.doFilter(
                    request,
                    response
            );

            return;
        }

        try {

            // TOKEN
            String jwt =
                    authHeader.substring(7);

            Claims claims =
                    Jwts.parserBuilder()
                            .setSigningKey(
                                    getSignKey()
                            )
                            .build()
                            .parseClaimsJws(jwt)
                            .getBody();

            String username =
                    claims.getSubject();

            // AUTH OBJECT
            UsernamePasswordAuthenticationToken
                    authToken =
                    new UsernamePasswordAuthenticationToken(

                            new User(
                                    username,
                                    "",
                                    Collections.emptyList()
                            ),

                            null,

                            Collections.emptyList()
                    );

            authToken.setDetails(

                    new WebAuthenticationDetailsSource()
                            .buildDetails(request)
            );

            // SET SECURITY CONTEXT
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authToken);

        } catch (Exception e) {

            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );

            response.getWriter().write(
                    "Invalid JWT Token"
            );

            return;
        }

        filterChain.doFilter(
                request,
                response
        );
    }
}