package cz.itnetwork.entity.repository;

import cz.itnetwork.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

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


    @Query("""
    SELECT i FROM InvoiceEntity i
    WHERE i.hidden = false
      AND (:buyerId IS NULL OR i.buyer.id = :buyerId)
      AND (:sellerId IS NULL OR i.seller.id = :sellerId)
      AND (:product IS NULL OR LOWER(i.product) LIKE LOWER(CONCAT('%', :product, '%')))
      AND (:minPrice IS NULL OR i.price >= :minPrice)
      AND (:maxPrice IS NULL OR i.price <= :maxPrice)
    ORDER BY i.issued DESC
    """)
    List<InvoiceEntity> findFiltered(
            @Param("buyerId") Long buyerId,
            @Param("sellerId") Long sellerId,
            @Param("product") String product,
            @Param("minPrice") Integer minPrice,
            @Param("maxPrice") Integer maxPrice,
            Pageable pageable
    );
}


