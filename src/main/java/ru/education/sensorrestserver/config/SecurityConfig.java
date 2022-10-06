package ru.education.sensorrestserver.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.education.sensorrestserver.security.AuthProviderImpl;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @author Kirill Popov
 */
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthProviderImpl authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .anyRequest().authenticated()
                )
                .httpBasic(withDefaults())
                .authenticationProvider(authProvider);
        return http.build();
    }
}
