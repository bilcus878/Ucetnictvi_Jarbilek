package cz.itnetwork.service;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.dto.InvoiceStatisticsDTO;
import cz.itnetwork.dto.mapper.InvoiceMapper;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import cz.itnetwork.entity.repository.InvoiceRepository;
import cz.itnetwork.entity.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private InvoiceMapper invoiceMapper;

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        InvoiceEntity entity = invoiceMapper.toEntity(invoiceDTO);

        Long sellerId = invoiceDTO.getSellerId() != null
                ? invoiceDTO.getSellerId()
                : (invoiceDTO.getSeller() != null ? invoiceDTO.getSeller().getId() : null);

        Long buyerId = invoiceDTO.getBuyerId() != null
                ? invoiceDTO.getBuyerId()
                : (invoiceDTO.getBuyer() != null ? invoiceDTO.getBuyer().getId() : null);

        if (sellerId == null || buyerId == null) {
            throw new IllegalArgumentException("Seller ID and Buyer ID must not be null");
        }

        PersonEntity seller = personRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller with id " + sellerId + " not found"));

        PersonEntity buyer = personRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException("Buyer with id " + buyerId + " not found"));

        entity.setSeller(seller);
        entity.setBuyer(buyer);

        entity = invoiceRepository.save(entity);
        return invoiceMapper.toDTO(entity);
    }

    @Override
    public void removeInvoice(long id) {
        InvoiceEntity existing = fetchInvoiceById(id);

        if (existing.isHidden()) {
            throw new IllegalStateException("Invoice with id " + id + " is already marked as deleted");
        }

        existing.setHidden(true);
        invoiceRepository.save(existing);
    }

    @Override
    public List<InvoiceDTO> getAll() {
        return invoiceRepository.findByHidden(false).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceDTO getInvoice(long invoiceId) {
        InvoiceEntity entity = fetchInvoiceById(invoiceId);
        return invoiceMapper.toDTO(entity);
    }

    @Override
    public List<InvoiceDTO> getInvoicesBySeller(Long sellerId) {
        return invoiceRepository.findBySellerIdAndHiddenFalse(sellerId).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<InvoiceDTO> getInvoicesByBuyer(Long buyerId) {
        return invoiceRepository.findByBuyerIdAndHiddenFalse(buyerId).stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InvoiceStatisticsDTO getInvoiceStatistics() {
        int currentYear = Year.now().getValue();

        BigDecimal currentYearSum = invoiceRepository.sumPriceByYearAndHiddenFalse(currentYear);
        BigDecimal allTimeSum = invoiceRepository.sumPriceAllTimeAndHiddenFalse();
        long invoicesCount = invoiceRepository.countByHiddenFalse();

        InvoiceStatisticsDTO stats = new InvoiceStatisticsDTO();
        stats.setCurrentYearSum(currentYearSum);
        stats.setAllTimeSum(allTimeSum);
        stats.setInvoicesCount(invoicesCount);

        return stats;
    }

    @Override
    public InvoiceDTO updateInvoice(long id, InvoiceDTO invoiceDTO) {
        InvoiceEntity existing = fetchInvoiceById(id);

        if (existing.isHidden()) {
            throw new IllegalStateException("Cannot update a deleted invoice");
        }

        existing.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        existing.setIssued(invoiceDTO.getIssued());
        existing.setDueDate(invoiceDTO.getDueDate());
        existing.setProduct(invoiceDTO.getProduct());
        existing.setPrice(invoiceDTO.getPrice());
        existing.setVat(invoiceDTO.getVat());
        existing.setNote(invoiceDTO.getNote());

        Long sellerId = invoiceDTO.getSellerId() != null
                ? invoiceDTO.getSellerId()
                : (invoiceDTO.getSeller() != null ? invoiceDTO.getSeller().getId() : null);

        Long buyerId = invoiceDTO.getBuyerId() != null
                ? invoiceDTO.getBuyerId()
                : (invoiceDTO.getBuyer() != null ? invoiceDTO.getBuyer().getId() : null);

        if (sellerId == null || buyerId == null) {
            throw new IllegalArgumentException("Seller ID and Buyer ID must not be null");
        }

        PersonEntity seller = personRepository.findById(sellerId)
                .orElseThrow(() -> new NotFoundException("Seller with id " + sellerId + " not found"));

        PersonEntity buyer = personRepository.findById(buyerId)
                .orElseThrow(() -> new NotFoundException("Buyer with id " + buyerId + " not found"));

        existing.setSeller(seller);
        existing.setBuyer(buyer);

        existing = invoiceRepository.save(existing);
        return invoiceMapper.toDTO(existing);
    }

    @Override
    public List<InvoiceDTO> getFilteredInvoices(Long buyerId, Long sellerId, String product, Integer minPrice, Integer maxPrice, Integer limit) {
        // Pokud limit není zadán nebo je menší než 1, nastavíme defaultní velký limit (např. 1000)
        int pageSize = (limit == null || limit < 1) ? 1000 : limit;

        List<InvoiceEntity> results = invoiceRepository.findFiltered(
                buyerId,
                sellerId,
                product,
                minPrice,
                maxPrice,
                PageRequest.of(0, pageSize)
        );

        return results.stream()
                .map(invoiceMapper::toDTO)
                .collect(Collectors.toList());
    }

    private InvoiceEntity fetchInvoiceById(long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Invoice with id " + id + " wasn't found in the database."));
    }
}
