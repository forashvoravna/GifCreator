package uz.lab.gifcreator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll() // Ochiq endpointlar
                        .anyRequest().authenticated() // Barcha boshqa endpointlar autentifikatsiyadan o'tadi
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/api/gif/**") // Muvaffaqiyatli kirgandan keyin URL
                        .userInfoEndpoint(userInfo -> userInfo.oidcUserService(new OidcUserService()))
                )
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
