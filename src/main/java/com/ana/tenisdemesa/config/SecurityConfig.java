package com.ana.tenisdemesa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${APP_LOGIN_USERNAME}")
    private String username;

    @Value("${APP_LOGIN_PASSWORD}")
    private String rawPassword;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails user = User.withUsername(username)
                .password(encoder.encode(rawPassword))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/css/**", "/js/**", "/images/**", "/webjars/**").permitAll()
                .requestMatchers("/actuator/**").permitAll()
                .requestMatchers("/login", "/logout").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form.permitAll())
            .rememberMe(remember -> remember
                    .tokenValiditySeconds(60 * 60 * 24 * 30)
            )
            .logout(logout -> logout.permitAll())
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new HtmxAwareAuthenticationEntryPoint())
            )
            .addFilterBefore(new HtmxAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
