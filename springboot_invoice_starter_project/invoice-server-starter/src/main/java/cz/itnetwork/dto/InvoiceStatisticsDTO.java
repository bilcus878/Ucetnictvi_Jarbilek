package cz.itnetwork.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceStatisticsDTO {
    private BigDecimal currentYearSum;
    private BigDecimal allTimeSum;
    private long invoicesCount;
}