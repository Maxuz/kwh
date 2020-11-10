package tk.maxuz.kwh.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"tk.maxuz.kwh.database.repository", "tk.maxuz.kwh.model"})
public class JpaConfig {
}
