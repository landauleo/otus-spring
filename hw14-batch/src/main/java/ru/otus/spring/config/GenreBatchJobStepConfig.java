package ru.otus.spring.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.MongoItemWriter;
import org.springframework.batch.item.data.builder.MongoItemWriterBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@AllArgsConstructor
public class GenreBatchJobStepConfig {

    public JobRepository jobRepository;
    public EntityManagerFactory entityManagerFactory;
    public PlatformTransactionManager platformTransactionManager;
    public MongoTemplate mongoTemplate;

    @Bean
    public Step genreToNoSqlStep() {
        return new StepBuilder("genreToNoSqlStep", jobRepository)
                .<ru.otus.spring.domain.sql.Genre, ru.otus.spring.domain.nosql.Genre>chunk(5, platformTransactionManager)
                .reader(genreJpaReader())
                .processor(ru.otus.spring.domain.nosql.Genre::of)
                .writer(genreMongoWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<ru.otus.spring.domain.sql.Genre> genreJpaReader() {
        return new JpaCursorItemReaderBuilder<ru.otus.spring.domain.sql.Genre>()
                .name("genreJpaReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select g from Genre g")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.domain.nosql.Genre> genreMongoWriter() {
        return new MongoItemWriterBuilder<ru.otus.spring.domain.nosql.Genre>()
                .collection("genre")
                .template(mongoTemplate)
                .build();
    }

}
