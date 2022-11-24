package com.sample.simpleproject.config;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DatabaseConfig {
    @PersistenceContext
    private EntityManager entityManager;

}
