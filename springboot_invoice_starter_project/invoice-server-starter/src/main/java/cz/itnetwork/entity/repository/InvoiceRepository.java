package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {

    List<InvoiceEntity> findByHidden(boolean hidden);

    List<InvoiceEntity> findByHiddenFalse();

    List<InvoiceEntity> findBySellerIdAndHiddenFalse(Long sellerId);

    List<InvoiceEntity> findByBuyerIdAndHiddenFalse(Long buyerId);

    // Statistiky ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    @Query("SELECT COALESCE(SUM(i.price), 0) FROM InvoiceEntity i WHERE YEAR(i.issued) = :year AND i.hidden = false")
    BigDecimal sumPriceByYearAndHiddenFalse(@Param("year") int year);

    @Query("SELECT COALESCE(SUM(i.price), 0) FROM InvoiceEntity i WHERE i.hidden = false")
    BigDecimal sumPriceAllTimeAndHiddenFalse();

    long countByHiddenFalse();
}

