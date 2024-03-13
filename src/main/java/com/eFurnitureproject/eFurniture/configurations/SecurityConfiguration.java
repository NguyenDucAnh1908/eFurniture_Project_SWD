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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Arrays;
import java.util.List;

import static com.eFurnitureproject.eFurniture.models.Enum.Permission.*;
import static com.eFurnitureproject.eFurniture.models.Enum.Role.ADMIN;
import static com.eFurnitureproject.eFurniture.models.Enum.Role.USER;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@CrossOrigin
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authentication -> authentication
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers(POST, "/api/v1/**").permitAll()

                        .requestMatchers(GET,"/api/v1/get-all-staff").hasRole("ADMIN")
                        .requestMatchers(GET,"api/v1/{{userId}}").hasRole("ADMIN")
                        .requestMatchers(GET,"/api/v1/get-all-user").hasRole("ADMIN")
                        .requestMatchers(GET,"/api/v1/get-all-staff-delivery").hasRole("ADMIN")
                        .requestMatchers(GET,"/api/v1/get-all-designer").hasRole("ADMIN")
                        .requestMatchers(GET,"/api/v1/**").permitAll()
                        .requestMatchers(PUT, "/api/v1/updateUser/**").permitAll()
                        .requestMatchers(DELETE, "/api/v1/deleteUser/**").hasRole("ADMIN")

                        .requestMatchers(GET,"api/v1/products/**").permitAll()
                        .requestMatchers(POST, "/api/v1/products").hasRole("ADMIN")
                        .requestMatchers(PUT,"/api/v1/products/**").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/api/v1/products/**").hasRole("ADMIN")
//


                        .requestMatchers("api/v1/tags-blog/**").permitAll()
                        .requestMatchers("api/v1/orders/**").permitAll()
                        .requestMatchers(POST,"api/v1/orders/**" ).hasRole("ADMIN")
                        .requestMatchers(PUT,"api/v1/orders/**" ).hasRole("ADMIN")
                        .requestMatchers(DELETE,"api/v1/orders/**").hasRole("ADMIN")

                        .requestMatchers("/api/v1/brand/**").permitAll()

                        .requestMatchers(POST,"/api/v1/blogs/**").hasRole("ADMIN")
                        .requestMatchers(PUT,"/api/v1/blogs/**").hasRole("ADMIN")
                        .requestMatchers(DELETE,"/api/v1/blogs/**").hasRole("ADMIN")


                        .requestMatchers("api/v1/tag_product").permitAll()
                        .requestMatchers("/api/delivery/**").hasAnyRole("ADMIN","STAFF_DELIVERY")
                        .requestMatchers("/api/v1/orders-detail/**").hasRole("ADMIN")


                        .requestMatchers("/api/v1/booking/**").hasAnyRole("ADMIN","DESIGNER")
                        .requestMatchers(POST, "/api/v1/register-booking").permitAll()
                        .requestMatchers(POST, "/api/v1/booking/register-project-booking").hasAnyRole("DESIGNER")
                        .requestMatchers(PUT,"/api/v1/booking/receive-booking-request/**").hasRole("DESIGNER")
                        .requestMatchers(PUT,"/api/v1/booking/updateProjectBooking/**").hasAnyRole("DESIGNER")
                        .requestMatchers(DELETE,"/api/v1/booking/cancel-booking/**").hasAnyRole("DESIGNER","ADMIN")


                        .requestMatchers("/api/v1/coupons/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/designs/**").hasAnyRole("ADMIN","DESIGNER")
                        .anyRequest().authenticated())

                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
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
                .logout(logoutRequest -> logoutRequest
                        .logoutUrl("/authentication/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request, response, authentication) ->
                                SecurityContextHolder.clearContext()))
                );
        return http.build();

    }


}