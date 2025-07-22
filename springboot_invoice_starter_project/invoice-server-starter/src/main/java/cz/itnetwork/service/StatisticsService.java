package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;

@Service
public class StatisticsService {

    private final InvoiceRepository invoiceRepository;
    private final PersonRepository personRepository;

    public StatisticsService(InvoiceRepository invoiceRepository, PersonRepository personRepository) {
        this.invoiceRepository = invoiceRepository;
        this.personRepository = personRepository;
    }

    public InvoiceStatisticsDTO getInvoiceStatistics() {
        int currentYear = Year.now().getValue();

        BigDecimal currentYearSum = invoiceRepository.sumPriceByYearAndHiddenFalse(currentYear);
        BigDecimal allTimeSum = invoiceRepository.sumPriceAllTimeAndHiddenFalse();
        long invoiceCount = invoiceRepository.countByHiddenFalse();

        return new InvoiceStatisticsDTO(currentYearSum, allTimeSum, invoiceCount);
    }

    public List<PersonStatisticsDTO> getPersonStatistics() {
        return personRepository.calculatePersonStatistics();
    }
}
