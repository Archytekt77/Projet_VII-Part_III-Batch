package com.loicmaria.batch.config;

import com.loicmaria.batch.tasklet.SendEmailTasklet;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;


@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private SendEmailTasklet sendEmailTasklet;

    @Bean
    public Step sendEmailStep() {
        return stepBuilderFactory.get("sendEmailStep")
                .tasklet(sendEmailTasklet)
                .build();
    }

    @Bean
    public Job sendEmailJob(Step sendEmailStep) {
        return jobBuilderFactory.get("sendEmailJob")
                .start(sendEmailStep)
                .build();
    }

}
