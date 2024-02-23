package cz.pesout.springbatchkata;

import cz.pesout.springbatchkata.domain.city.CityRecord;
import cz.pesout.springbatchkata.step.CityStep;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.core.io.ByteArrayResource;

public class CityStepTests {

    @Test
    public void testCityReader() throws Exception {
        var cityStep = new CityStep(null);
        var reader = cityStep.reader();

        reader.setResource(new ByteArrayResource("""
                "ID";"Name";"CountryCode";"District";"Population"
                1;"Kabul";"AFG";"Kabol";1780000
                2;"Qandahar";"AFG";"Qandahar";237500
                """.getBytes()));
        reader.open(new ExecutionContext());

        var kabulRecord = reader.read();

        Assertions.assertNotNull(kabulRecord);
        Assertions.assertEquals(1, kabulRecord.id());
        Assertions.assertEquals("Kabul", kabulRecord.name());
        Assertions.assertEquals("AFG", kabulRecord.countryCode());
        Assertions.assertEquals("Kabol", kabulRecord.district());
        Assertions.assertEquals(1_780_000, kabulRecord.population());

        var qandaharRecord = reader.read();
        Assertions.assertNotNull(qandaharRecord);
        Assertions.assertEquals(2, qandaharRecord.id());
        Assertions.assertEquals("Qandahar", qandaharRecord.name());
        Assertions.assertEquals("AFG", qandaharRecord.countryCode());
        Assertions.assertEquals("Qandahar", qandaharRecord.district());
        Assertions.assertEquals(237_500, qandaharRecord.population());

        var nullRecord = reader.read();
        Assertions.assertNull(nullRecord);

        reader.close();
    }

    @Test
    public void testCityProcessor() throws Exception {
        var cityStep = new CityStep(null);
        var cityRecord = new CityRecord(3339, "Praha", "CZE", "Hlavní město Praha", 1_200_000);

        var city = cityStep.processor().process(cityRecord);

        Assertions.assertNotNull(city);
        Assertions.assertEquals(3339, city.getId());
        Assertions.assertEquals("Praha", city.getName());
        Assertions.assertEquals("CZE", city.getCountryCode());
        Assertions.assertEquals("Hlavní město Praha", city.getDistrict());
        Assertions.assertEquals(1_200_000, city.getPopulation());
    }

}
