package ru.javaops.startup.app.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.javaops.startup.app.AppUser;
import ru.javaops.startup.app.oauth2.AuthUserService;
import ru.javaops.startup.user.model.Role;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {
    public static final String API_PATH = "/api";

    private final AppProps appProps;
    private final AuthUserService authUserService;

    // https://stackoverflow.com/questions/62622390/548473
    @Bean
    public UserDetailsManager userDetailsManager() {
        User.UserBuilder builder = User.builder();
        List<UserDetails> users = appProps.getUsers().stream()
                .map(au -> builder.username(au.name()).password(au.password()).authorities(au.roles()).build())
                .toList();
        return new InMemoryUserDetailsManager(users) {
            @Override
            public AppUser loadUserByUsername(String username) throws UsernameNotFoundException {
                return new AppUser(super.loadUserByUsername(username));
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                        .requestMatchers(API_PATH + "/admin/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").hasRole(Role.ADMIN.name())
                        .requestMatchers(API_PATH + "/**").hasAnyRole(Role.ADMIN.name(), Role.PARTNER.name())
                        .requestMatchers("/ui/auth/**", "/view/auth/**").authenticated()
                        .anyRequest().permitAll())
                .formLogin(flc -> flc.loginPage("/view/login"))
                .httpBasic(withDefaults())
                .logout(lc -> lc.logoutUrl("/view/logout").logoutSuccessUrl("/"))
                .oauth2Login(olc -> olc.defaultSuccessUrl("/ui/auth/profile")
                        .loginPage("/view/login")
                        .userInfoEndpoint(uiec -> uiec.userService(authUserService)))
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}