package com.bucketstore.common.jpa.config;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.ImplicitEntityNameSource;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;

public class UpperSnakeCaseImplicitNamingStrategy extends ImplicitNamingStrategyJpaCompliantImpl {

    @Override
    public Identifier determinePrimaryTableName(ImplicitEntityNameSource source) {
        if (source == null) return null;
        String name = source.getEntityNaming().getJpaEntityName()
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toUpperCase();
        return new Identifier(name, false);
    }
}