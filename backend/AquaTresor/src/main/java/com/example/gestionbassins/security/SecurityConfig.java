package com.example.gestionbassins.security;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.gestionbassins.security.JWTAuthorizationFilter;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled= true)
public class SecurityConfig {
	
	/*
	@Bean
	public SecurityFilterChain filterChain (HttpSecurity http) throws
		Exception{
			http.sessionManagement( session ->
			session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			
			.csrf( csrf -> csrf.disable())
			
		//////ce code j'ai le fait en webconfig!!!!!!!
					/*
					 * 
					 * .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
					 * 
					 * @Override public CorsConfiguration getCorsConfiguration(HttpServletRequest
					 * request) { CorsConfiguration cors = new CorsConfiguration();
					 * 
					 * cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
					 * cors.setAllowedMethods(Collections.singletonList("*"));
					 * cors.setAllowedHeaders(Collections.singletonList("*"));
					 * cors.setExposedHeaders(Collections.singletonList("Authorization")); return
					 * cors; } }))
					 */
			
		/**	
			.authorizeHttpRequests( requests ->
			requests.requestMatchers(HttpMethod.GET, "api/all").hasAnyAuthority("ADMIN", "USER")
			

						.requestMatchers(HttpMethod.GET,"/api/getbyid/**").hasAnyAuthority("ADMIN", "USER")
						//.requestMatchers(HttpMethod.POST,"/api/addev/**").hasAnyAuthority("ADMIN")
						.requestMatchers(HttpMethod.PUT,"/api/updateev/**").hasAnyAuthority("ADMIN")
						.requestMatchers(HttpMethod.DELETE,"/api/deleteev/**").hasAnyAuthority("ADMIN")
						.requestMatchers("/api/image/**").permitAll()
						.requestMatchers("/themes/**").hasAnyAuthority("ADMIN", "USER")
						.requestMatchers("/themes/themes/**").hasAnyAuthority("ADMIN", "USER")
					.anyRequest().authenticated() )
			
			.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class);
			
					
			
		return http.build();
	}
}*/
/***
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/******************Permit all the users* correct one*////////////	
	/*
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .cors().configurationSource(new CorsConfigurationSource() {
	            @Override
	            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	                CorsConfiguration config = new CorsConfiguration();
	                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	                config.setAllowCredentials(true);
	                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
	                config.setExposedHeaders(Arrays.asList("Authorization"));
	                config.setMaxAge(3600L);
	                return config;
	            }
	        }).and()
	        .authorizeHttpRequests()
	            .anyRequest().permitAll()  // Permet l'accès à toutes les requêtes sans restriction
	            
	        .and()
	        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}*/
	/*
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .cors().configurationSource(new CorsConfigurationSource() {
	            @Override
	            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	                CorsConfiguration config = new CorsConfiguration();
	                config.setAllowedOrigins(Collections.singletonList("http://localhost:2400")); // Update this line
	                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	                config.setAllowCredentials(true);
	                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
	                config.setExposedHeaders(Arrays.asList("Authorization"));
	                config.setMaxAge(3600L);
	                return config;
	            }
	        }).and()
	        .authorizeHttpRequests()
	            .anyRequest().permitAll()  // Allow all requests (for testing purposes)
	        .and()
	        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}*/
	
	/*
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // ✅ Permit all requests
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://yourfrontend.com")); // ✅ Adjust as needed
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        cors.setExposedHeaders(Collections.singletonList("Authorization"));
        cors.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }*/
	
	/*     LAST ONE STAGE PFE */
	/*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // ✅ Permet toutes les requêtes

        
        return http.build();
    }*/
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class) // Add JWT filter
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/api/addbassin").hasAuthority("ADMIN") 
	            .requestMatchers("/api/all").hasAnyAuthority("ADMIN", "USER")
	            
	            .requestMatchers("/api/updatebassin").hasAuthority("ADMIN")
	            .requestMatchers("/api/deletebassin").hasAuthority("ADMIN")
	            
	            .requestMatchers("/api/image/**").permitAll()
	            
	            .requestMatchers("/api/categories/**").hasAnyAuthority("ADMIN", "USER")
	    		//.requestMatchers("/themes/themes/**").hasAnyAuthority("ADMIN", "USER")
	    		
	            .anyRequest().permitAll() // Allow all other requests
	        );

	    return http.build();
	}


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:2400")); // ✅ Autoriser plusieurs origines
        cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowCredentials(true);
        cors.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
        cors.setExposedHeaders(Collections.singletonList("Authorization"));
        cors.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
	
    /*****
     * 
     @Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf().disable()
	        .sessionManagement()
	            .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .cors().configurationSource(new CorsConfigurationSource() {
	            @Override
	            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
	                CorsConfiguration config = new CorsConfiguration();
	                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	                config.setAllowCredentials(true);
	                config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
	                config.setExposedHeaders(Arrays.asList("Authorization"));
	                config.setMaxAge(3600L);
	                return config;
	            }
	        }).and()
	        .authorizeHttpRequests()
	            .anyRequest().permitAll()  // Permet l'accès à toutes les requêtes sans restriction
	            
	        .and()
	        .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	}
     */
	/*
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(HttpMethod.POST, "/api/addbassin").hasAuthority("ADMIN") // 🔒 Seuls les ADMIN peuvent ajouter un bassin
	            .anyRequest().permitAll() // 🌍 Toutes les autres requêtes sont autorisées
	        );

	    return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration cors = new CorsConfiguration();
	    cors.setAllowedOrigins(Arrays.asList("http://localhost:4200", "http://localhost:2400")); // ✅ Autoriser plusieurs origines
	    cors.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	    cors.setAllowCredentials(true);
	    cors.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin"));
	    cors.setExposedHeaders(Collections.singletonList("Authorization"));
	    cors.setMaxAge(3600L);

	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", cors);
	    return source;
	}
	*/


}








/********************** New
import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled= true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .csrf( csrf -> csrf.disable()) 
        .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cors = new CorsConfiguration();
                cors.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                cors.setAllowedMethods(Collections.singletonList("*"));
                cors.setAllowedHeaders(Collections.singletonList("*"));
                cors.setExposedHeaders(Collections.singletonList("Authorization"));
                
                return cors;
            }
        }))
            .authorizeHttpRequests(requests -> requests
            		.requestMatchers("/api/image/**").permitAll()
                .requestMatchers("/api/all/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/getbyid/**").hasAnyAuthority("ADMIN", "USER")
               // .requestMatchers(HttpMethod.POST, "/api/addev/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/updateev/**").hasAuthority("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/deleteev/**").hasAuthority("ADMIN")
                /*.requestMatchers("/gen/**").hasAnyAuthority("ADMIN", "USER")
                .requestMatchers("/api/vetcgen/**").hasAnyAuthority("ADMIN", "USER")*/
/***
                .anyRequest().authenticated())
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
  
}
}

*//////////////////////




	


