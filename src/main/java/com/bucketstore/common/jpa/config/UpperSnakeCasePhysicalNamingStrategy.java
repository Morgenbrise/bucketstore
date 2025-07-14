package com.bucketstore.common.jpa.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class UpperSnakeCasePhysicalNamingStrategy extends PhysicalNamingStrategyStandardImpl {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return format(name);
    }

    @Override
    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return format(name);
    }

    private Identifier format(Identifier identifier) {
        if (identifier == null) return null;
        String newName = identifier.getText()
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();
        return Identifier.toIdentifier(newName);
    }
}