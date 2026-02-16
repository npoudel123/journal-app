package net.engineeringdigest.journalApp.config;


import net.engineeringdigest.journalApp.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public SpringSecurity(UserDetailsServiceImpl userDetailsService){
        this.userDetailsServiceImpl = userDetailsService;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Disable CSRF (Rest -> No CSRF)
                .csrf().disable()
                //No Session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/journal/v1/**").authenticated()
                .antMatchers("/user/v1/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN") //this is role base authentication
                .anyRequest().permitAll()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
