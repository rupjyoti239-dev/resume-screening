package com.example.resumeScreening.Be.config;

import com.example.resumeScreening.Be.util.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth->auth
                        .requestMatchers("/api/auth/**").permitAll()

                        //password reset
                        .requestMatchers(HttpMethod.POST,"/api/user/reset-password").hasAnyRole("ADMIN","USER","HR")


                        // Category endpoints
                        .requestMatchers(HttpMethod.POST, "/api/categories").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/categories/{categoryId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/categories").hasAnyRole("ADMIN", "HR", "USER")

                        // Job endpoints
                        .requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("HR")
                        .requestMatchers(HttpMethod.PATCH, "/api/jobs/{jobId}").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/api/jobs/my-jobs").hasRole("HR")
                        .requestMatchers(HttpMethod.PATCH, "/api/jobs/{jobId}/status").hasRole("HR")
                        .requestMatchers(HttpMethod.DELETE, "/api/jobs/{jobId}").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/api/jobs/search").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/jobs").hasAnyRole("ADMIN", "HR", "USER")

                        //apply
                        .requestMatchers(HttpMethod.POST, "/api/applications/{jobId}").hasRole("USER")
                        .requestMatchers(HttpMethod.GET, "/api/jobs/{jobId}").hasRole("HR")
                        .requestMatchers(HttpMethod.GET, "/api/jobs//{applicationId}/resume").hasRole("HR")

                        //save job
                        .requestMatchers("/api/save-job").hasRole("USER")

                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Replace below with your frontend's origin (e.g., http://localhost:3000)
        config.setAllowedOrigins(List.of("http://localhost:5173")); // Or use config.addAllowedOrigin("*") for public
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setExposedHeaders(List.of("Authorization")); // If you return JWT in header
        config.setAllowCredentials(true); // Allow cookies / credentials

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/", config); // Apply to all paths
        return source;
    }

}
