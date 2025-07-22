package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import java.util.List;

public interface InvoiceService {

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    void removeInvoice(long id);

    List<InvoiceDTO> getAll();

    InvoiceDTO getInvoice(long invoiceId);

    List<InvoiceDTO> getInvoicesBySeller(Long sellerId);

    List<InvoiceDTO> getInvoicesByBuyer(Long buyerId);

    InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO);

    InvoiceStatisticsDTO getInvoiceStatistics();

}

