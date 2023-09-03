package ru.otus.spring.config;

import com.mongodb.MongoClientSettings;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig implements MongoClientSettingsBuilderCustomizer {

    @Override
    public void customize(MongoClientSettings.Builder clientSettingsBuilder) {
        clientSettingsBuilder
                .readConcern(ReadConcern.LOCAL)
                .writeConcern(WriteConcern.MAJORITY)
                .readPreference(ReadPreference.primary())
                .retryReads(true)
                .retryWrites(true);
    }

}
