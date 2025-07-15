package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface InvoiceMapper {

    // Při převodu z DTO na entitu ignorujeme seller a buyer, protože je nastavujeme ručně v service
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    InvoiceEntity toEntity(InvoiceDTO dto);

    // Při převodu z entity na DTO mapujeme seller.id do sellerId a buyer.id do buyerId
    // a zároveň mapujeme kompletní objekty seller a buyer pomocí PersonMapperu
    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "buyerId", source = "buyer.id")
    @Mapping(target = "seller", source = "seller")
    @Mapping(target = "buyer", source = "buyer")
    InvoiceDTO toDTO(InvoiceEntity entity);
}








