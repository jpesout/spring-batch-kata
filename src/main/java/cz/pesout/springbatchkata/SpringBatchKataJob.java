package cz.pesout.springbatchkata;

import lombok.extern.java.Log;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Log
@Component
public class SpringBatchKataJob {

    @Bean
    public Job importDataJob(JobRepository jobRepository, Step importCityStep, Step importCountryStep) {
        return new JobBuilder("importDataJob", jobRepository)
                .start(importCityStep)
                .next(importCountryStep)
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("beforeJob");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("afterJob");
                    }
                })
                .build();
    }
}
