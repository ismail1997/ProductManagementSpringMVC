package com.ismail.products.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration @EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    BCryptPasswordEncoder passwordEncoder ;
    @Autowired
    private DataSource dataSource;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       BCryptPasswordEncoder bCryptPasswordEncoder = getBCPE();
        System.out.println(bCryptPasswordEncoder.encode("1234"));
        /* auth.inMemoryAuthentication()
                .withUser("admin")
                .password(bCryptPasswordEncoder.encode("1234"))
                .roles("ADMIN","USER");
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(bCryptPasswordEncoder.encode("1234"))
                .roles("USER");
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder());*/
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select username as principal, password as credentials,active from users where username = ?")
            .authoritiesByUsernameQuery("select user as principal , role as role from users_roles where user = ?")
            .rolePrefix("ROLE_")
            .passwordEncoder(getBCPE());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().loginPage("/login");
        http.authorizeRequests().antMatchers("/admin/*").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/user/*").hasRole("USER");
        http.exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    BCryptPasswordEncoder getBCPE(){
        return new BCryptPasswordEncoder();
    }
}
