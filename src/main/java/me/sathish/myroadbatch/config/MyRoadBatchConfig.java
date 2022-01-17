package me.sathish.myroadbatch.config;

import me.sathish.myroadbatch.data.Road;
import me.sathish.myroadbatch.data.RoadInput;
import me.sathish.myroadbatch.listener.RoadsJobCompletionNotificationListener;
import me.sathish.myroadbatch.processor.RoadDataProcessor;
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

@Configuration
public class MyRoadBatchConfig {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    private final String[] DATA_STRINGS = new String[]{"objectID", "road_name", "surface_type", "miles",
            "total_miles", "rustic_road_number", "community", "county", "line_to_web_page",
            "shape_len"};
    @Bean
    public FlatFileItemReader<RoadInput> reader() {
        return new FlatFileItemReaderBuilder<RoadInput>().name("MatchItemReader")
                .resource(new ClassPathResource("Rustic_Roads.csv")).delimited().names(DATA_STRINGS).
                fieldSetMapper(new BeanWrapperFieldSetMapper<RoadInput>() {
                    {
                        setTargetType(RoadInput.class);
                    }
                }).build();
    }

    @Bean
    public RoadDataProcessor processor() {
        return new RoadDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Road> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Road>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("Insert Into Road(road_name, surface_type, miles, total_miles, rustic_road_number," +
                        " community, county,line_to_web_page," +
                        " shape_len) VALUES(:road_name, :surface_type,:miles, " +
                        ":total_miles,:rustic_road_number,:community,:county,:line_to_web_page," +
                        ":shape_len)").dataSource(dataSource).build();
    }

    @Bean
    public Job importRoadUserJob(RoadsJobCompletionNotificationListener listener, Step step1) {
        return jobBuilderFactory.get("importRoadUserJob").
                incrementer(new RunIdIncrementer()).listener(listener).
                flow(step1).end().build();
    }

    @Bean
    public Step step1(JdbcBatchItemWriter<Road> writer) {
        return stepBuilderFactory.
                get("step2").<RoadInput, Road>chunk(10).
                reader(reader()).
                processor(processor()).
                writer(writer).
                build();
    }
}
