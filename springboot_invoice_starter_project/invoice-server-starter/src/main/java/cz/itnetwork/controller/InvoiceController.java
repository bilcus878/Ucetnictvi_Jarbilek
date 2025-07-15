package cz.itnetwork.controller;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InvoiceDTO addInvoice(@RequestBody InvoiceDTO invoiceDTO) {
        return invoiceService.addInvoice(invoiceDTO);
    }

    @GetMapping
    public List<InvoiceDTO> getInvoices() {
        return invoiceService.getAll();
    }

    @GetMapping("/{invoiceId}")
    public InvoiceDTO getInvoiceById(@PathVariable long invoiceId) {
        return invoiceService.getInvoice(invoiceId);
    }

    @DeleteMapping("/{invoiceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInvoice(@PathVariable long invoiceId) {
        invoiceService.removeInvoice(invoiceId);
    }

    @GetMapping("/sent/{sellerId}")
    public List<InvoiceDTO> getInvoicesBySeller(@PathVariable Long sellerId) {
        return invoiceService.getInvoicesBySeller(sellerId);
    }

    @GetMapping("/received/{buyerId}")
    public List<InvoiceDTO> getInvoicesByBuyer(@PathVariable Long buyerId) {
        return invoiceService.getInvoicesByBuyer(buyerId);
    }

}

