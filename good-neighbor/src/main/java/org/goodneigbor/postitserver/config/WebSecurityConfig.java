package org.goodneigbor.postitserver.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import org.goodneigbor.postitserver.config.security.RestAuthenticationEntryPoint;
import org.goodneigbor.postitserver.config.security.RestAuthenticationFailureHandler;
import org.goodneigbor.postitserver.config.security.RestAuthenticationSuccessHandler;
import org.goodneigbor.postitserver.service.global.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public enum Role {
        ROLE_BOARD_WRITE, ROLE_USER_WRITE;
    }

    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_URL = "/logout";

    @Autowired
    private UserService userService;

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private RestAuthenticationSuccessHandler successHandler;

    @Autowired
    private RestAuthenticationFailureHandler failureHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        auth.authenticationProvider(authProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());

        http.formLogin().loginPage(LOGIN_URL).successHandler(successHandler).failureHandler(failureHandler);
        http.logout()
            .logoutUrl(LOGOUT_URL)
            .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse
                .setStatus(HttpServletResponse.SC_OK));

        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }

}
