package exp.web.config;

import com.vaadin.flow.spring.security.VaadinSecurityConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Vaadin сам обрабатывает CSRF
                .with(VaadinSecurityConfigurer.vaadin(), configurer -> {
                    // указываем Vaadin страницу логина
                    configurer.loginView(StaticData.authEndPoint);
                });

        return http.build();
    }
}