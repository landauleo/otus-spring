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
public class BookBatchJobStepConfig {

    public JobRepository jobRepository;
    public EntityManagerFactory entityManagerFactory;
    public PlatformTransactionManager platformTransactionManager;
    public MongoTemplate mongoTemplate;

    @Bean
    public Step bookToNoSqlStep() {
        return new StepBuilder("bookToNoSqlStep", jobRepository)
                .<ru.otus.spring.domain.sql.Book, ru.otus.spring.domain.nosql.Book>chunk(5, platformTransactionManager)
                .reader(bookJpaReader())
                .processor(ru.otus.spring.domain.nosql.Book::of)
                .writer(bookMongoWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<ru.otus.spring.domain.sql.Book> bookJpaReader() {
        return new JpaCursorItemReaderBuilder<ru.otus.spring.domain.sql.Book>()
                .name("bookJpaReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select b from Book b")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.domain.nosql.Book> bookMongoWriter() {
        return new MongoItemWriterBuilder<ru.otus.spring.domain.nosql.Book>()
                .collection("book")
                .template(mongoTemplate)
                .build();
    }

}
