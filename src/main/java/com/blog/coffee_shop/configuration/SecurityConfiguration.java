package com.blog.coffee_shop.configuration;

import com.blog.coffee_shop.configuration.jwt.JWTConfigurer;
import com.blog.coffee_shop.configuration.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

import static com.blog.coffee_shop.util.Constant.UserRole.ROLE_ADMIN;
import static com.blog.coffee_shop.util.Constant.UserRole.ROLE_SUPER_ADMIN;
import static com.blog.coffee_shop.util.Constant.UserRole.ROLE_USER;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration {

    private final TokenProvider tokenProvider;

    public SecurityConfiguration(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private JWTConfigurer jwtSecurityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
             .csrf()
                .disable()
             .headers()
                .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
                    .and()
                .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
                    .and()
                .permissionsPolicy()
                    .policy("camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()")
                    .and()
                .frameOptions()
                    .deny()
                    .and()
             .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
             .authorizeRequests()
                .antMatchers("/api/login")
                    .permitAll()
                .antMatchers("/api/**")
                    .hasAnyAuthority(ROLE_USER.value, ROLE_ADMIN.value, ROLE_SUPER_ADMIN.value)
                .anyRequest()
                    .authenticated()
                    .and()
             .httpBasic()
                .and()
             .apply(jwtSecurityConfigurerAdapter());

        return http.build();
    }
}
