package com.loicmaria.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher jobLauncher;

    private final ItemReader<List<String>> apiReader;
    private final ItemProcessor<List<String>, List<String>> emailProcessor;
    private final ItemWriter<List<String>> emailWriter;

    @Autowired
    private Job myJob;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory,
                              StepBuilderFactory stepBuilderFactory,
                              ItemReader<List<String>> apiReader,
                              ItemProcessor<List<String>, List<String>> emailProcessor,
                              ItemWriter<List<String>> emailWriter) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.apiReader = apiReader;
        this.emailProcessor = emailProcessor;
        this.emailWriter = emailWriter;
    }

    @Scheduled(cron = "0 1 0 * * ?")
    public void scheduleJob() {
        try {
            jobLauncher.run(myJob, new JobParameters());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Bean
    public Step myStep() {
        return stepBuilderFactory.get("myStep")
                .<List<String>, List<String>>chunk(10)
                .reader(apiReader)
                .processor(emailProcessor)
                .writer(emailWriter)
                .build();
    }

    @Bean
    public Job myJob(Step myStep) {
        return jobBuilderFactory.get("myJob")
                .start(myStep)
                .build();
    }
}
