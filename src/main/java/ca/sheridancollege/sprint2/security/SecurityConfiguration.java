package ca.sheridancollege.sprint2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private LoginAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/adminHome").hasRole("ADMIN")
                .antMatchers("/myAccount/**").hasAnyRole("USER", "ADMIN")
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/", "/css/**", "/img/**", "/jsp/**", "/**").permitAll()
                .antMatchers("/console/**").permitAll() //this helps us get access to the console
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .failureUrl("/login-error")
                .and()
                .logout() //
                .invalidateHttpSession(true) //this clears the information after you log out
                .clearAuthentication(true) //this also clears information after you log out
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))//our mapping to the page after logging out
                .logoutSuccessUrl("/")
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);

        //These lines below will help us access the h2 console. You would not use these in a production environment.
        http
                .csrf().disable();
        http
                .headers().frameOptions().disable();

    }

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public BCryptPasswordEncoder passworEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passworEncoder());

    }
}
