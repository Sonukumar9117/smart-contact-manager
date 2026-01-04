package com.scm.scm.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.boot.internal.Abstract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
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
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.authorizeHttpRequests(authorize->{
            authorize.requestMatchers("/user/**","/home","/about","/logout","/services","/user-detail").authenticated();
            authorize.anyRequest().permitAll();
//            authorize.requestMatchers("/login","/logout").permitAll().anyRequest().authenticated();
        }).formLogin(
                httpSecurityFormLoginConfigurer ->
                {
                    httpSecurityFormLoginConfigurer
                            .loginPage("/login")
                            .loginProcessingUrl("/authenticate")
                            .successForwardUrl("/home");
                    httpSecurityFormLoginConfigurer.usernameParameter("email");
                    httpSecurityFormLoginConfigurer.passwordParameter("password");
                }

        );

        
        return httpSecurity.build();
    }

//     @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
//         return new DaoAuthenticationProvider(userDetailsService);
//    }
}
