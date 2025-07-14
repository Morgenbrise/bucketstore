package com.bucketstore.common.jpa.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnMissingBean(name = "jpaAutoNamingCustomizer")
public class JpaNamingAutoConfiguration {

    @Bean
    public HibernatePropertiesCustomizer jpaAutoNamingCustomizer() {
        return hibernateProperties -> {
            hibernateProperties.put(
                    "hibernate.implicit_naming_strategy",
                    UpperSnakeCaseImplicitNamingStrategy.class.getName()
            );
            hibernateProperties.put(
                    "hibernate.physical_naming_strategy",
                    UpperSnakeCasePhysicalNamingStrategy.class.getName()
            );
        };
    }
}