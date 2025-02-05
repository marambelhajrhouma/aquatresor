package com.example.AquaTresor.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

	    // Exclure les requêtes pour /register et /login de la vérification du JWT
	    if (request.getRequestURI().contains("/api/client/login") || request.getRequestURI().contains("/api/client/register")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    String jwt = request.getHeader("Authorization");

	    if (jwt == null || !jwt.startsWith(SecParams.PREFIX)) {
	        filterChain.doFilter(request, response);
	        return;
	    }

	    try {
	        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecParams.SECRET)).build();
	        jwt = jwt.substring(SecParams.PREFIX.length());
	        DecodedJWT decodedJWT = verifier.verify(jwt);

	        String username = decodedJWT.getSubject();
	        String role = decodedJWT.getClaim("roles").asString();

	        Collection<GrantedAuthority> authorities = new ArrayList<>();
	        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

	        UsernamePasswordAuthenticationToken user =
	                new UsernamePasswordAuthenticationToken(username, null, authorities);
	        SecurityContextHolder.getContext().setAuthentication(user);

	    } catch (Exception e) {
	        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid JWT: " + e.getMessage());
	        return;
	    }

	    filterChain.doFilter(request, response);
	}

    
}
