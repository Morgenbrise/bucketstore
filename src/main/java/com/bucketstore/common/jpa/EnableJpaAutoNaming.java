package com.bucketstore.common.jpa;

import com.bucketstore.common.jpa.config.JpaNamingAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(JpaNamingAutoConfiguration.class)
public @interface EnableJpaAutoNaming {
}