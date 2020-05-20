package main.security;

import main.security.jwt.JwtSecurityConfigurer;
import main.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .formLogin().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/registration").permitAll()
                .antMatchers(HttpMethod.GET, "/car/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/car/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/car").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/car").permitAll()
                .antMatchers(HttpMethod.GET, "/master/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/master/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/master").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/master").permitAll()
                .antMatchers(HttpMethod.GET, "/service/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/service/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/service").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/service").permitAll()
                .antMatchers(HttpMethod.GET, "/work/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT, "/work/{id}").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/work").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET, "/work").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
