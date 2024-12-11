package br.com.domingosdelazer.SistemaGestao.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permite todos os endpoints
                        .allowedOrigins("https://www.domingodelazer.click", "https://api.domingodelazer.click") // Adicione todas as origens necessárias
                        .allowedMethods("*") // Permite todos os métodos HTTP
                        .allowedHeaders("*") // Permite todos os cabeçalhos
                        .allowCredentials(true) // Permite cookies/autenticação
                        .maxAge(3600); // Cache da requisição OPTIONS por 1 hora
            }
        };
    }
}

