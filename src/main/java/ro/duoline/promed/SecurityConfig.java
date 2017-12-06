package ro.duoline.promed;

import javax.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetailsService;
import ro.duoline.promed.domains.security.Authority;

@Configuration
  //ro.duoline.promed.jpa
@EnableWebSecurity
@EnableJpaRepositories("ro.duoline.promed.jpa")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final Authority AUTHORITY_SU_ADMIN = new Authority("ADMIN");
    public static final Authority AUTHORITY_MEDIC = new Authority("MEDIC");
    public static final Authority AUTHORITY_PACIENT = new Authority("PACIENT");

    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Bean(name = "passwordEncoder")
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuth = new DaoAuthenticationProvider();
        daoAuth.setUserDetailsService(userDetailsService);
        daoAuth.setPasswordEncoder(passwordEncoder());
        return daoAuth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().ignoringAntMatchers("/h2-console/**").disable().headers().frameOptions().disable()
                .and().authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and().authorizeRequests().antMatchers("/webjars/**").permitAll()
                .and().authorizeRequests().antMatchers("/static/**").permitAll()
                .and().authorizeRequests().antMatchers("/image/**").permitAll()
                .and().authorizeRequests().antMatchers("/images/medics/**").hasAuthority(AUTHORITY_MEDIC.getAuthority())
                .and().authorizeRequests().antMatchers("/rest/**").permitAll()
                .and().authorizeRequests().antMatchers("/js/**").permitAll()
                .and().authorizeRequests().antMatchers("/user/**").permitAll()
                .and().formLogin().loginPage("/login").permitAll()
                .and().authorizeRequests().antMatchers("/admin/**").hasAuthority(AUTHORITY_SU_ADMIN.getAuthority())//hasRole(ROLE_ADMIN.getRole()).anyRequest().authenticated()
                .and().exceptionHandling().accessDeniedPage("/access_denied");

    }
}
