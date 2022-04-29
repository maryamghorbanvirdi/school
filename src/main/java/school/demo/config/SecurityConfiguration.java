package school.demo.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableAutoConfiguration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    PasswordEncoder encoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder();
    auth
        .inMemoryAuthentication()
        .withUser("user")
        .password(encoder.encode("password"))
        .roles("STUDENT", "TEACHER")
        .and()
        .withUser("admin")
        .password(encoder.encode("admin"))
        .roles("TEACHER");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/scores/{scoreId}").permitAll()
        .and()
        .authorizeRequests()
        .antMatchers("/scores").hasAuthority(Constants.ADD_COURSE)
        .antMatchers("/scores").hasAuthority(Constants.GET_ALL_COURSE)
        .antMatchers("/scores/{scoreId}/update").hasAuthority(Constants.EDIT_COURSE)
        .antMatchers("/actuator/health", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .httpBasic();
  }
}
