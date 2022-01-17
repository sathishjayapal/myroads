package me.sathish.myroadbatch.config;

import me.sathish.myroadbatch.data.County;
import me.sathish.myroadbatch.data.CountyInput;
import me.sathish.myroadbatch.listener.CountyJobCompletionNotificationListener;
import me.sathish.myroadbatch.processor.CountyDataProcessor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

//@Configuration
//@EnableBatchProcessing
public class MyCountyBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private final String[] DATA_STRINGS = new String[]{"objectID", "regionName", "countCode",
            "countyName", "countyFipsCode", "shapeArea", "shapeLen"};

    @Bean
    public FlatFileItemReader<CountyInput> importCountyReader() {
        return new FlatFileItemReaderBuilder<CountyInput>().name("CountyItemReader")
                .resource(new ClassPathResource("County_Boundaries_24K.csv"))
                .delimited().names(DATA_STRINGS)
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>() {
                    {
                        setTargetType(CountyInput.class);
                    }
                }).build();
    }

    @Bean
    public CountyDataProcessor importCountyProcessor() {
        return new CountyDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<County> importCountyWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<County>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("Insert Into County(county_code, county_name, region_name) VALUES(:county_code, " +
                        ":county_name,:region_name)")
                .dataSource(dataSource).build();
    }

    @Bean
    public Job importCountyUserJob(CountyJobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importCountyUserJob").
                incrementer(new RunIdIncrementer()).listener(listener).
                flow(step1).end().build();
    }

    @Bean
    public Step importCountyUserJobStep(JdbcBatchItemWriter<County> writer) {
        return stepBuilderFactory.
                get("step3").<CountyInput, County>chunk(10)
                .reader(importCountyReader())
                .processor(importCountyProcessor())
                .writer(writer)
                .build();
    }
}
