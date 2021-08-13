package io.github.xpeteliu.config.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditConfig {

    @Bean
    AuditorAware<Long> auditorProvider() {
        return new SpringSecurityAuditorAware();
    }
}

class SpringSecurityAuditorAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {
        try {
            Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
            return Optional.of(userId);
        } catch (NullPointerException e) {
            return Optional.empty();
        }
    }
}
