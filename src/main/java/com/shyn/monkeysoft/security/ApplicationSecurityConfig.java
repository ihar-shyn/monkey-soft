package com.shyn.monkeysoft.security;

import com.shyn.monkeysoft.user.MonkeyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final MonkeyUserService monkeyUserService;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
       http
               .csrf().disable()
               .headers().frameOptions().disable()
               .and()
               .authorizeRequests()
               .antMatchers("/css/*", "/js/*", "/images/*", "/h2/*")
               .permitAll()
               .anyRequest()
               .authenticated()
               .and()
               .formLogin()
               .loginPage("/login")
               .permitAll()
               .defaultSuccessUrl("/users", true);
   }

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, MonkeyUserService monkeyUserService) {
        this.passwordEncoder = passwordEncoder;
        this.monkeyUserService = monkeyUserService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(monkeyUserService);
        return provider;
    }


}
