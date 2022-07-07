package com.shyn.monkeysoft.security;

import com.shyn.monkeysoft.user.MonkeyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final PasswordEncoder passwordEncoder;
    private final MonkeyUserService monkeyUserService;

    @Autowired
    public SecurityConfiguration(PasswordEncoder passwordEncoder, MonkeyUserService monkeyUserService) {
        this.passwordEncoder = passwordEncoder;
        this.monkeyUserService = monkeyUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/css/*", "/js/*", "/images/*", "/h2/*", "/login")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/users", true);

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(monkeyUserService);
        return provider;
    }

}
