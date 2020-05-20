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
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
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
                    .antMatchers("/registration").hasRole("ADMIN")
                    .antMatchers(HttpMethod.GET, "/car/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/car/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/car/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/car").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/car").hasRole("USER")

                    .antMatchers(HttpMethod.GET, "/master/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/master/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/master/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/master").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/master").hasRole("USER")

                    .antMatchers(HttpMethod.GET, "/service/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/service/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/service/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/service").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/service").hasRole("USER")

                    .antMatchers(HttpMethod.GET, "/work/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.PUT, "/work/{id}").hasRole("USER")
                    .antMatchers(HttpMethod.DELETE, "/work/{id}").hasRole("ADMIN")
                    .antMatchers(HttpMethod.POST, "/work").hasRole("USER")
                    .antMatchers(HttpMethod.GET, "/work").hasRole("USER")

                    .anyRequest().authenticated()
                .and()
                    .apply(new JwtSecurityConfigurer(jwtTokenProvider));
    }
}
