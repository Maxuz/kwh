package dev.maxuz.kwh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"dev.maxuz.kwh.database.repository", "dev.maxuz.kwh.model"})
public class JpaConfig {
}
