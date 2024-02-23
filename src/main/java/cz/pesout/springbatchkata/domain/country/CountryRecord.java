package cz.pesout.springbatchkata.domain.country;

import java.math.BigDecimal;

public record CountryRecord(String code, String name, String continent, String region, BigDecimal surfaceArea,
                            Integer indepYear, Integer population, BigDecimal lifeExpectancy, BigDecimal gnp,
                            BigDecimal gnpOld, String localName, String governmentForm, String headOfState,
                            Integer capital, String code2) {
}
