package com.intern.OnlineBookStore.config;

import com.intern.OnlineBookStore.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.GET,"/").hasAnyRole("user","admin")

                        .requestMatchers(HttpMethod.GET,"/allUsers").hasAnyRole("user","admin")

                        .requestMatchers(HttpMethod.GET,"/addUser").hasRole("admin")
                        .requestMatchers(HttpMethod.POST,"/save").hasRole("admin")

                        .requestMatchers(HttpMethod.GET,"/update/**").hasRole("admin")
                        .requestMatchers(HttpMethod.PUT,"/update/**").hasRole("admin")

                        .requestMatchers(HttpMethod.GET,"/deleteUser/**").hasRole("admin")
                        .anyRequest().authenticated()
                )

                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());
                /*.formLogin(form -> form
                    .loginPage("/login")
                );*/
                /*.oauth2ResourceServer((oauth2) -> oauth2
                        .jwt(Customizer.withDefaults())
                )*/
//                .oauth2Login(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    /*@Bean
    public JwtDecoder jwtDecoder() {
        return JwtDecoders.fromIssuerLocation("https://my-auth-server.com");
    }*/

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService);
    }




//    Done for In Memory Access
    /*@Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("12345"))
                .roles("USER","ADMIN")
                .build();
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("abcdef"))
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }*/
}
