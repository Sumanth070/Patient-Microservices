package com.microservice.notification.repository;

import com.microservice.notification.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TemplateRepository extends JpaRepository<Template, Long> {

    Optional<Template> findByTemplateKey(String key);
}
