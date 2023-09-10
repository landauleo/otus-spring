package ru.otus.spring.config;

import lombok.RequiredArgsConstructor;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.conversions.Bson;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

/**
 * The identifier for a given HealthIndicator is the name of the bean without the HealthIndicator suffix, if it exists.
 */
@Component
@RequiredArgsConstructor
public class DataSourceHealthIndicator implements HealthIndicator {

    public final MongoTemplate mongoTemplate;

    @Override
    public Health health() {
        try {
            Bson command = new BsonDocument("ping", new BsonInt64(1));
            mongoTemplate.getDb().runCommand(command);
            return Health.up().withDetail("dataSourceOk", "(^____^) It's alright, bro, keep calm and WORK!!!").build();
        } catch (Exception e) {
            return Health.up().withDetail("dataSourceError", "8 (>_<) 8  Houston, we have a problem: " + e.getMessage()).build();
        }
    }

}