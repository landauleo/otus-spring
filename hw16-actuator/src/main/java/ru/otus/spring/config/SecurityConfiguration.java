package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    //to get to know more https://docs.spring.io/spring-security/reference/servlet/authorization/authorize-http-requests.html
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/error", "/static/**", "/css/**", "/images/**", "/login", "/actuator", "/actuator/**").permitAll()
                        .requestMatchers("/index").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasAuthority("ROLE_LIBRARIAN")
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAuthority("ROLE_LIBRARIAN")
                        .requestMatchers(HttpMethod.GET, "/api/**").hasAnyAuthority("ROLE_VISITOR", "ROLE_LIBRARIAN")
                        .anyRequest().denyAll()
                )
                .formLogin()
                .defaultSuccessUrl("/index", true)
                .failureForwardUrl("/error");

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
