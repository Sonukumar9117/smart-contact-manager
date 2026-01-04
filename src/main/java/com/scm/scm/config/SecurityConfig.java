package com.scm.scm.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
//    @Bean
//    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder){
//        UserDetails userDetails= User.withUsername("sonu123").password(passwordEncoder.encode("sonu123")).build();
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    @Bean
    public  PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity){

        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**","/home").authenticated();
            authorize.anyRequest().permitAll();
        }).formLogin(Customizer.withDefaults());
        return httpSecurity.build();
    }

//     @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
//         return new DaoAuthenticationProvider(userDetailsService);
//    }
}
