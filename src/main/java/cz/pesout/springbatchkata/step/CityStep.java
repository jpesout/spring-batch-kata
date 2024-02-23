package cz.pesout.springbatchkata.step;

import cz.pesout.springbatchkata.domain.city.City;
import cz.pesout.springbatchkata.domain.city.CityRecord;
import cz.pesout.springbatchkata.domain.city.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.RecordFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class CityStep {

    private final CityRepository cityRepository;

    @Bean
    public Step importCityStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCityStep", jobRepository)
                .<CityRecord, City> chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();
    }

    public FlatFileItemReader<CityRecord> reader() {
        return new FlatFileItemReaderBuilder<CityRecord>()
                .name("cityItemReader")
                .resource(new ClassPathResource("city.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(";")
                .names("id", "name", "countryCode", "district", "population")
                .fieldSetMapper(new RecordFieldSetMapper<>(CityRecord.class))
                .build();
    }

    public ItemProcessor<CityRecord, City> processor() {
        return item -> City.builder()
                .id(item.id())
                .name(item.name())
                .countryCode(item.countryCode())
                .district(item.district())
                .population(item.population())
                .build();
    }

    public ItemWriter<City> writer() {
        var writer = new RepositoryItemWriter<City>();
        writer.setRepository(cityRepository);
        return writer;
    }

}
