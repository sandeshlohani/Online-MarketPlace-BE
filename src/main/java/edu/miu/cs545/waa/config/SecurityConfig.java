package edu.miu.cs545.waa.config;


import edu.miu.cs545.waa.constant.SecurityConstants;
import edu.miu.cs545.waa.enums.RoleType;
import edu.miu.cs545.waa.filter.JwtAuthorizationFilter;
import edu.miu.cs545.waa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.servlet.support.csrf.CsrfRequestDataValueProcessor;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.support.RequestDataValueProcessor;

import java.util.Arrays;

/**
 * SecurityConfiguration  class  it is responsible for  giving access to specific url, specific resource specific role
 *
 * @version 1.0.0
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${waa.super.user.username}")
    private String superAdminUsername;
    @Value("${waa.super.user.password}")
    private String superAdminPassword;
    private final UserService appUserService;

    @Autowired
    public SecurityConfig(UserService appUserService) {
        this.appUserService = appUserService;
    }

    /**
     * Method to  giving access to specific url, specific resource to specific role
     *
     * @param http HttpSecurity
     * @throws Exception is thrown if user is not authenticated
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests()
                .antMatchers("/webjars/**", "/swagger-resources/**").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/h2console/**").permitAll()
                .antMatchers("/swagger-ui.html", "/swagger-ui/**").permitAll()
                .antMatchers("/v3/**").permitAll()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/users/**").permitAll()
                .antMatchers("/api/v1/public/**").permitAll()
                .antMatchers("/api/v1/admins/**").hasAuthority(RoleType.ADMIN.name())
                .antMatchers("/api/v1/buyers/**").hasAuthority(RoleType.BUYER.name())
                .antMatchers("/api/v1/products/**").hasAnyAuthority(RoleType.SELLER.name(), RoleType.BUYER.name())
                .antMatchers("/api/v1/orders/generate-excel").permitAll()
                .antMatchers("/api/v1/orders/**").hasAuthority(RoleType.BUYER.name())
                .antMatchers("/api/v1/reviews/**").hasAuthority(RoleType.BUYER.name())
//        .hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .antMatchers("/404").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(
                        new AntPathRequestMatcher("/login?logout"))
                .logoutSuccessUrl("/").permitAll()
                .and().csrf().disable()
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    public RequestDataValueProcessor requestDataValueProcessor() {
        return new CsrfRequestDataValueProcessor();
    }

    /**
     * Method for setting  username password role either dynamically from database or statically in hard core program
     *
     * @param auth AuthenticationManagerBuilder
     */
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.inMemoryAuthentication()
                .withUser(superAdminUsername)
                .password(passwordEncoder().encode(superAdminPassword))
                .authorities(SecurityConstants.ROLE_ADMIN);

        auth.userDetailsService(appUserService);
    }


    /**
     * Method for encoding password
     *
     * @return encoder password encorder is returned
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Method CorsConfigurationSource for allowing resource files
     *
     * @return source CorsConfigurationSource
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT", "OPTIONS"));
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    /**
     * Created Bean for Authentication manager
     *
     * @return authenticationManager Object
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
