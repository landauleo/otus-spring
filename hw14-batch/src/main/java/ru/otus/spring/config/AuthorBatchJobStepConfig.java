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
public class AuthorBatchJobStepConfig {

    public JobRepository jobRepository;
    public EntityManagerFactory entityManagerFactory;
    public PlatformTransactionManager platformTransactionManager;
    public MongoTemplate mongoTemplate;

    @Bean
    public Step authorToNoSqlStep() {
        return new StepBuilder("authorToNoSqlStep", jobRepository)
                .<ru.otus.spring.domain.sql.Author, ru.otus.spring.domain.nosql.Author>chunk(5, platformTransactionManager)
                .reader(authorJpaReader())
                .processor(ru.otus.spring.domain.nosql.Author::of)
                .writer(authorMongoWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<ru.otus.spring.domain.sql.Author> authorJpaReader() {
        return new JpaCursorItemReaderBuilder<ru.otus.spring.domain.sql.Author>()
                .name("authorJpaReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select a from Author a")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.domain.nosql.Author> authorMongoWriter() {
        return new MongoItemWriterBuilder<ru.otus.spring.domain.nosql.Author>()
                .collection("author")
                .template(mongoTemplate)
                .build();
    }

}
