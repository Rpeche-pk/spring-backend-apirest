package com.lrpa.springboot.backend.apirest.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final CorsConfigurationSource corsConfigurationSource;

    public ResourceServerConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET,"/api/clientes","/api/clientes/page/**","/api/uploads/img/**","/images/**").permitAll()
                .antMatchers(HttpMethod.POST,"/api/register").permitAll()
                /*.antMatchers(HttpMethod.GET,"/api/clientes/{id}").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/clientes/upload").hasAnyRole("USER","ADMIN")
                .antMatchers(HttpMethod.POST,"/api/clientes").hasRole("ADMIN")
                .antMatchers("/api/clientes/**").hasRole("ADMIN")*/
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource);
    }
}
