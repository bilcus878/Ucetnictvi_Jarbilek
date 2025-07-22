package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.PersonStatisticsDTO;
import cz.itnetwork.service.StatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/statistics")
public class StatisticsController {

    private final StatisticsService statisticsService;

    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    // Endpoint pro obecné statistiky faktur
    @GetMapping("/invoices")
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        return statisticsService.getInvoiceStatistics();
    }

    // Endpoint pro statistiky podle osob/společností
    @GetMapping("/persons")
    public List<PersonStatisticsDTO> getPersonStatistics() {
        return statisticsService.getPersonStatistics();
    }
}

