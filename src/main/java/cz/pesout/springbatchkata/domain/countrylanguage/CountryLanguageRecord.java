package cz.pesout.springbatchkata.domain.countrylanguage;

import java.math.BigDecimal;

public record CountryLanguageRecord(String countryCode, String language, String isOfficial, BigDecimal percentage) {
}
