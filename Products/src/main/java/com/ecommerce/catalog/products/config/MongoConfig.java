package com.ecommerce.catalog.products.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Component;

@Component
public class MongoConfig implements CommandLineRunner {
    @Autowired
    private MongoTemplate mongoTemplate;
//Creacion de indice para consultas optimizadas
    @Override
    public void run(String... args) throws Exception {
        boolean exists = mongoTemplate.indexOps("products").getIndexInfo().stream()
                .anyMatch(info -> info.getName().contains("name"));
        if (!exists) {
            TextIndexDefinition index = new TextIndexDefinition.TextIndexDefinitionBuilder()
                    .onField("name").build();
            mongoTemplate.indexOps("products").ensureIndex(index);
            System.out.println("Index de texto creado para el campo nombre");
        }
    }
}
