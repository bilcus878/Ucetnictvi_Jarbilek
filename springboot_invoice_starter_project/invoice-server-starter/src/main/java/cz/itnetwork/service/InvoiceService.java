package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import java.util.List;

public interface InvoiceService {

    // Přidá novou fakturu na základě dat z InvoiceDTO a vrátí vytvořený objekt faktury
    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    // Odstraní fakturu podle jejího ID (typicky označení faktury jako smazané nebo fyzické smazání)
    void removeInvoice(long id);

    // Vrátí seznam všech faktur jako seznam DTO objektů
    List<InvoiceDTO> getAll();

    // Vrátí detail faktury podle jejího ID
    InvoiceDTO getInvoice(long invoiceId);

    // Vrátí seznam faktur, které byly vystaveny konkrétnímu prodejci (sellerId)
    List<InvoiceDTO> getInvoicesBySeller(Long sellerId);

    // Vrátí seznam faktur, které byly přijaty konkrétním kupujícím (buyerId)
    List<InvoiceDTO> getInvoicesByBuyer(Long buyerId);

    // Aktualizuje existující fakturu podle jejího ID a dat z InvoiceDTO, vrátí aktualizovanou fakturu
    InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO);
}

