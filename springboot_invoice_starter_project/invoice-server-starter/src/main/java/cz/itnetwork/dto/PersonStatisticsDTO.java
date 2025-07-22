package cz.itnetwork.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class PersonStatisticsDTO {
    private Long personId;
    private String personName;
    private BigDecimal revenue;

    // Konstruktor pro JPQL – bere Number a převádí na BigDecimal
    public PersonStatisticsDTO(Long personId, String personName, Number revenue) {
        this.personId = personId;
        this.personName = personName;
        this.revenue = revenue == null ? BigDecimal.ZERO : new BigDecimal(revenue.toString());
    }
}



