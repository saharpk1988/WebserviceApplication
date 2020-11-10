package com.spk.web.myfirstws.security;

import com.spk.web.myfirstws.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final UserService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.requiresChannel()
//                .antMatchers("/*").requiresSecure();
                http.csrf().disable().authorizeRequests()
                        .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
                        .permitAll()
                        .antMatchers(HttpMethod.GET, SecurityConstants.VERIFICATION_EMAIL_URL)
                        .permitAll()
                        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_REQUEST_URL)
                        .permitAll()
                        .antMatchers(HttpMethod.POST, SecurityConstants.PASSWORD_RESET_URL)
                        .permitAll()
                        .antMatchers(SecurityConstants.H2_CONSOLE)
                        .permitAll()
                        .anyRequest().authenticated()
                        .and().addFilter(getAuthenticationFilter())
                        .addFilter(new AuthorizationFilter(authenticationManager()))
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //disable frame options on http headers which prevents the browser to load
        //the page in html tags like <iframe> for security reasons, to make the H2 database console
        //to open up in browser window for testing purposes
        http.headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder web) throws Exception {
        web.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    public AuthenticationFilter getAuthenticationFilter() throws Exception {
        final AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager());
        authenticationFilter.setFilterProcessesUrl("/users/login");
        return authenticationFilter;
    }
}
