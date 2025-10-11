package com.example.aufgaben_backend;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration cfg = new CorsConfiguration();
        // ⬇️ ВРЕМЕННО разрешаем локальные источники + будущие домены фронта
        cfg.setAllowedOrigins(List.of(
                "http://localhost:5500",   // Live Server (VS Code)
                "http://127.0.0.1:5500",
                "http://localhost:5173",   // Vite/Dev
                "http://localhost:8081",   // если фронт будете раздавать отдельно
                "https://<your-github-username>.github.io",
                "https://<your-site>.netlify.app",
                "https://<your-site>.vercel.app"
        ));
        cfg.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS"));
        cfg.setAllowedHeaders(List.of("*"));
        cfg.setAllowCredentials(false); // куки/сессии не нужны

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", cfg);
        return new CorsFilter(src);
    }
}
