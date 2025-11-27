package br.uniesp.si.techback.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private static final String[] SWAGGER_PATHS = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/h2-console/**",
            "/api/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção CSRF, que é comum em APIs REST
                .csrf(csrf -> csrf.disable())

                // Define a autorização de requisições
                .authorizeHttpRequests(auth -> auth
                        // Permite acesso irrestrito aos caminhos do Swagger e H2
                        .requestMatchers(SWAGGER_PATHS).permitAll()
                        // Qualquer outra requisição deve ser autenticada (padrão)
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean // O Spring irá gerenciar esta instância
    public PasswordEncoder passwordEncoder() {
        // BCrypt é um algoritmo seguro e eficiente para hashing de senhas.
        return new BCryptPasswordEncoder();
    }
}