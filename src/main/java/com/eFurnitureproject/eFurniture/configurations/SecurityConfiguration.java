package com.eFurnitureproject.eFurniture.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static com.eFurnitureproject.eFurniture.models.Enum.Role.ADMIN;
import static com.eFurnitureproject.eFurniture.models.Enum.Role.USER;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@CrossOrigin
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
//    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configure(http))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authentication -> authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/api/v1/**").permitAll()
//                        .requestMatchers("/api/v1/products/get_all").hasAnyRole(USER.name())
//                        .requestMatchers(GET, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_VIEW.name(), STAFF_VIEW.name())
//                        .requestMatchers(POST, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_CREATE.name(), STAFF_CREATE.name())
//                        .requestMatchers(PUT, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_UPDATE.name(), STAFF_UPDATE.name())
//                        .requestMatchers(DELETE, "/api/v1/managemnet/**").hasAnyAuthority(ADMIN_DELETE.name(), STAFF_DELETE.name())

                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable);
//                    http.cors(new Customizer<CorsConfigurer<HttpSecurity>>() {
//                        @Override
//                        public void customize(CorsConfigurer<HttpSecurity> httpSecurityCorsConfigurer) {
//                            CorsConfiguration configuration = new CorsConfiguration();
//                            configuration.setAllowedOrigins(List.of("*"));
//                            configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
//                            configuration.setAllowedHeaders(
//                                    Arrays.asList("Authorization", "content-type", "x-auth-token", "Accept",
//                                            "Accept-Encoding", "Connection", "Access-Control-Request-Method", "Access-Control-Request-Headers")
//                            );
//                            configuration.setExposedHeaders(List.of("x-auth-token"));
//                            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//                            source.registerCorsConfiguration("/**", configuration);
//                            httpSecurityCorsConfigurer.configurationSource(source);
//                        }
//
//                    });
//                .oauth2Login(o -> o
//                        .successHandler(customOAuth2AuthenticationSuccessHandler)
//                        .failureHandler((request, response, exception) -> {
//                            request.getSession().setAttribute("error.message", exception.getMessage());
//                        })
//                ;
        return http.build();

    }


}