package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    // Vrátí seznam faktur, kde je nastaveno pole 'hidden' na hodnotu parametru (true/false)
    List<InvoiceEntity> findByHidden(boolean hidden);

    // Vrátí seznam faktur, které nejsou skryté (hidden = false)
    List<InvoiceEntity> findByHiddenFalse();

    // Vrátí seznam faktur, které patří konkrétnímu prodávajícímu (sellerId) a nejsou skryté
    List<InvoiceEntity> findBySellerIdAndHiddenFalse(Long sellerId);

    // Vrátí seznam faktur, které patří konkrétnímu kupujícímu (buyerId) a nejsou skryté
    List<InvoiceEntity> findByBuyerIdAndHiddenFalse(Long buyerId);

}
