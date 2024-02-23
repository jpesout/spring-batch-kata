package cz.pesout.springbatchkata.domain.city;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    Integer id;

    String name;
    String countryCode;
    String district;
    Integer population;

}
