package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Základní URL adresa pro všechny metody v tomto kontroleru
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // Vytvoření nové faktury pomocí HTTP POST požadavku
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // HTTP 201 při úspěchu
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO); // Zavolání servisní metody
    }

    // Získání seznamu všech nezakázaných (nezakrytých) faktur
    @GetMapping
    public List<InvoiceDTO> getInvoices() {
        return invoiceService.getAll(); // Vrací seznam všech faktur
    }

    // Získání detailu jedné faktury podle jejího ID
    @GetMapping("/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable long invoiceId) {
        return invoiceService.getInvoice(invoiceId);
    }

    // "Smazání" faktury – nastaví ji jako skrytou (hidden = true)
    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // HTTP 204 při úspěchu, bez obsahu v odpovědi
    public void deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }

    // Získání všech faktur, které vystavila osoba se zadaným ID
    @GetMapping("/sent/{sellerId}")
    public List<InvoiceDTO> getInvoicesBySeller(@PathVariable Long sellerId) {
        return invoiceService.getInvoicesBySeller(sellerId);
    }

    // Získání všech faktur, které přijala osoba se zadaným ID
    @GetMapping("/received/{buyerId}")
    public List<InvoiceDTO> getInvoicesByBuyer(@PathVariable Long buyerId) {
        return invoiceService.getInvoicesByBuyer(buyerId);
    }

}