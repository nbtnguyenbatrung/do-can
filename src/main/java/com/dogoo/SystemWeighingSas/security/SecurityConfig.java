package com.dogoo.SystemWeighingSas.security;

import com.dogoo.SystemWeighingSas.filter.CustomAuthenticationFilter;
import com.dogoo.SystemWeighingSas.filter.CustomAuthorizationFilter;
import com.dogoo.SystemWeighingSas.unitity.token.JwtService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;

    public SecurityConfig(UserDetailsService userDetailsService, PasswordEncoder bCryptPasswordEncoder, JwtService jwtService) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter =
                new CustomAuthenticationFilter(authenticationManagerBean(), jwtService);
        customAuthenticationFilter.setFilterProcessesUrl("/dogoo/login");
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers(
                        "/dogoo/login",
                        "/public/dogoo/**"
                )
                .permitAll()
                .antMatchers(
                        HttpMethod.GET, "/o/dogoo/**")
                .hasAnyAuthority("admin", "adminUser", "user")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(
                        new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .cors();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
