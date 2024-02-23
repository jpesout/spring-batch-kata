package cz.pesout.springbatchkata.domain.country;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    @Id
    String code;
    String name;
    @Enumerated(EnumType.STRING)
    Continent continent;
    String region;
    BigDecimal surfaceArea;
    Integer indepYear;
    Integer population;
    BigDecimal lifeExpectancy;
    BigDecimal gnp;
    BigDecimal gnpOld;
    String localName;
    String governmentForm;
    String headOfState;
    Integer capital;
    String code2;
}
