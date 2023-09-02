package ru.otus.spring.shell;

import java.time.Duration;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class MigrationLauncher {

    private final JobLauncher jobLauncher;

    private final Job jobOnMigrationSqlToNosql;

    @ShellMethod(value = "Migrate sql to nosql", key = {"ms"})
    public void migrateSqlToNoSql() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        log.info("========================================");
        log.info("Job migrateSqlToNoSql started");
        LocalDateTime start = LocalDateTime.now();
        jobLauncher.run(jobOnMigrationSqlToNosql, new JobParameters());
        LocalDateTime end = LocalDateTime.now();
        log.info("Job migrateSqlToNoSql ended and took " +  Duration.between(start, end).getSeconds() + " sec");
        log.info("========================================");
    }
}
