package scotip.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customCompanyDetailsService")
    UserDetailsService companyDetailsService;


    /**
     * Allows to use a password encoder for password.
     *
     * @param auth
     * @throws Exception
     */
    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(companyDetailsService).passwordEncoder(getPasswordEncoder());

    }

    /**
     * Password crypto
     *
     * @return
     */
    private PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Configure the common security settings
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("Configuration security");

        http
                // always create a session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)

                // allows assets access
                .and()
                .authorizeRequests()
                .antMatchers("/assets/**").permitAll()

                // no pages restricted
                .anyRequest().permitAll()

                // alls pages beginning by /u are protected
                .antMatchers("/u/**").authenticated()
                .and()


                // login now
                .formLogin().loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
        ;


    }


}