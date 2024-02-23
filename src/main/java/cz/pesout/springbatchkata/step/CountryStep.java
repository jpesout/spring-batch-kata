package cz.pesout.springbatchkata.step;

import cz.pesout.springbatchkata.domain.country.Continent;
import cz.pesout.springbatchkata.domain.country.Country;
import cz.pesout.springbatchkata.domain.country.CountryRecord;
import cz.pesout.springbatchkata.domain.country.CountryRepository;
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

import java.math.BigDecimal;

@RequiredArgsConstructor
@Configuration
public class CountryStep {

    private final CountryRepository countryRepository;

    @Bean
    public Step importCountryStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importCountryStep", jobRepository)
                .<CountryRecord, Country> chunk(100, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();
    }

    public FlatFileItemReader<CountryRecord> reader() {
        return new FlatFileItemReaderBuilder<CountryRecord>()
                .name("countryItemReader")
                .resource(new ClassPathResource("country.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(";")
                .names("code", "name", "continent", "region", "surfaceArea", "indepYear", "population",
                        "lifeExpectancy", "gnp", "gnpOld", "localName", "governmentForm", "headOfState",
                        "capital", "code2")
                .fieldSetMapper(new RecordFieldSetMapper<>(CountryRecord.class))
                .build();
    }

    public ItemProcessor<CountryRecord, Country> processor() {
        return item -> Country.builder()
                .code(item.code())
                .name(item.name())
                .continent(Continent.valueOf(item.continent().replace(" ", "_").toUpperCase()))
                .region(item.region())
                .surfaceArea(item.surfaceArea())
                .indepYear(item.indepYear())
                .population(item.population())
                .lifeExpectancy(item.lifeExpectancy())
                .gnp(item.gnp())
                .gnpOld(item.gnpOld())
                .localName(item.localName())
                .governmentForm(item.governmentForm())
                .headOfState(item.headOfState())
                .capital(item.capital())
                .code2(item.code2())
                .build();
    }

    public ItemWriter<Country> writer() {
        var writer = new RepositoryItemWriter<Country>();
        writer.setRepository(countryRepository);
        return writer;
    }

}
