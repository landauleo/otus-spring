package ru.otus.spring.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@SuppressWarnings("all")
public class SpringBatchConfig {

    @Autowired
    public JobRepository jobRepository;

    @Bean
    public Job jobOnMigrationSqlToNosql(Step authorToNoSqlStep, Step genreToNoSqlStep, Step bookToNoSqlStep, Step commentToNoSqlStep) {
        return new JobBuilder("sqlToNosqlMigrationJob", jobRepository)
                .start(authorToNoSqlStep)
                .next(genreToNoSqlStep)
                .next(bookToNoSqlStep)
                .next(commentToNoSqlStep)
                .build();
    }

}
