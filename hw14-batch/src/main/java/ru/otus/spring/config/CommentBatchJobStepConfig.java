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
public class CommentBatchJobStepConfig {

    public JobRepository jobRepository;
    public EntityManagerFactory entityManagerFactory;
    public PlatformTransactionManager platformTransactionManager;
    public MongoTemplate mongoTemplate;

    @Bean
    public Step commentToNoSqlStep() {
        return new StepBuilder("commentToNoSqlStep", jobRepository)
                .<ru.otus.spring.domain.sql.Comment, ru.otus.spring.domain.nosql.Comment>chunk(5, platformTransactionManager)
                .reader(commentJpaReader())
                .processor(ru.otus.spring.domain.nosql.Comment::of)
                .writer(commentMongoWriter())
                .build();
    }

    @Bean
    public JpaCursorItemReader<ru.otus.spring.domain.sql.Comment> commentJpaReader() {
        return new JpaCursorItemReaderBuilder<ru.otus.spring.domain.sql.Comment>()
                .name("commentJpaReader")
                .entityManagerFactory(entityManagerFactory)
                .queryString("select c from Comment c")
                .build();
    }

    @Bean
    public MongoItemWriter<ru.otus.spring.domain.nosql.Comment> commentMongoWriter() {
        return new MongoItemWriterBuilder<ru.otus.spring.domain.nosql.Comment>()
                .collection("comment")
                .template(mongoTemplate)
                .build();
    }

}
