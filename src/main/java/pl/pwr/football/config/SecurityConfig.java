package pl.pwr.football.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import pl.pwr.football.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, LoginSuccessHandler successHandler) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Ścieżki statyczne (CSS, obrazy) i login są dostępne dla wszystkich
                        .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                        // Konkretne uprawnienia do hubów (RBAC)
                        .requestMatchers("/admin/**").hasRole("Administrator")
                        .requestMatchers("/trener/**").hasRole("Trener")
                        .requestMatchers("/sedzia/**").hasRole("Sedzia")
                        .requestMatchers("/pilkarz/**").hasRole("Pilkarz")

                        // Wszystko inne wymaga zalogowania
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/login") // Własny widok
                        .successHandler(successHandler) // Nasze przekierowanie
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Używamy MD5, ponieważ tak przygotowałeś dane w SQL (funkcja MD5())
        // Uwaga: W systemach produkcyjnych używa się BCrypt!
        return new MessageDigestPasswordEncoder("MD5");
    }
}