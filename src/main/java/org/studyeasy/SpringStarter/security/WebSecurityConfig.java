package org.studyeasy.SpringStarter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    private static final String[] WHITELIST = {
            "/", "/login", "/register", "/db-console/**", "/css/**", "/fonts/**", "/images/**", "/js/**", "/home","/forgot-password","/reset-password","/change-password","/api/v1/**","/api/v1"
    };

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for easier testing (re-enable in production)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(WHITELIST).permitAll()  // Allow access to these paths without authentication
                        .requestMatchers("/profile/**").authenticated()  // Require authentication for profile
                        .requestMatchers("/posts/add").authenticated()
                        .requestMatchers("/update_photo").authenticated()  // Other protected routes
                        .requestMatchers("/posts/**").permitAll()  // Public access to posts
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // Role-based access control
                        .requestMatchers("/editor/**").hasAnyRole("ADMIN", "EDITOR")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll())
                .httpBasic(withDefaults())
                .rememberMe(rememberMe -> rememberMe.rememberMeParameter("remember-me"))
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable())  // Disable for H2 console
                );

        return http.build();
    }
}