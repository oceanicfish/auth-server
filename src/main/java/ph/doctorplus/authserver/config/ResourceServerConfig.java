package ph.doctorplus.authserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ph.doctorplus.authserver.service.CustomUserDetailsService;

import java.util.Arrays;

@Configuration
@EnableResourceServer
//@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
public class ResourceServerConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService = new CustomUserDetailsService();

    @Override
    public void configure(HttpSecurity http) throws Exception {

        // by default uses a Bean by the name of corsConfigurationSource
        http.cors();
        http.authorizeRequests()
                .antMatchers("/", "/index**", "/login**", "/signup**", "/oauth/**").permitAll()
                .antMatchers("/css/**", "/js/**", "/bootstrap/**", "/plugins/**", "/less/**", "/angularjs/**").permitAll()
                .anyRequest().authenticated()
                .and()
//                .formLogin().loginPage("/login").successForwardUrl("/user/info").permitAll()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .rememberMe()
                .and()
//                .logout().logoutUrl("/log_out").logoutSuccessUrl("/").permitAll();
                .logout().logoutUrl("/log_out").permitAll();

        http.csrf().disable();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        /**
         * using JPA to get user info from a DB
         * password encoder -> encoding password in DB
         */
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS);
    }

    /**
     * this bean is for resolve the Javascript error of
     * Cross-origin redirection to http://localhost:8087/login?error denied by Cross-Origin Resource Sharing policy:
     * Origin http://localhost is not allowed by Access-Control-Allow-Origin.
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
