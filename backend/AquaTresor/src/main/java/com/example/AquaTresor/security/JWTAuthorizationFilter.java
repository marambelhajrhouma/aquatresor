package com.example.AquaTresor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || !jwtToken.startsWith(SecParams.PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Vérifier et décoder le token JWT
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SecParams.SECRET))
                    .build()
                    .verify(jwtToken.substring(SecParams.PREFIX.length()));

            String email = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email,
                null,
                roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Accès interdit : token invalide");
        }
    }
}