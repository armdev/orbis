package io.project.app;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 *
 * @author armen
 */
@EnableWebSecurity
public class WebSecurityConfig extends
        WebSecurityConfigurerAdapter {

  //  @Override
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
        http
                .cors()
                .and()
                .csrf().disable();
                
//        http.csrf().disable().exceptionHandling()
//                .authenticationEntryPoint(
//                        (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                
//                .and().authorizeRequests().antMatchers("/**").authenticated().and().httpBasic();
    }
}
