package com.hanghae.concert_reservation.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Profile("test")
@Component
public class DatabaseCleanUp implements InitializingBean {

    private final EntityManager entityManager;

    private List<String> tableNames = new ArrayList<>();

    @Autowired
    public DatabaseCleanUp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void afterPropertiesSet() {
        entityManager.getMetamodel().getEntities().stream()
                .filter(it -> it.getJavaType().getAnnotation(Table.class) != null)
                .map(it -> it.getJavaType().getAnnotation(Table.class).name())
                .forEach(tableNames::add);
    }

    @Transactional
    public void execute() {
        entityManager.flush();
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY FALSE").executeUpdate();
        for (String tableName : tableNames) {
            // Avoid initializing auto_increment IDs
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
        }
        entityManager.createNativeQuery("SET REFERENTIAL_INTEGRITY TRUE").executeUpdate();
    }
}
