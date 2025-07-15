package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = PersonMapper.class)
public interface InvoiceMapper {

    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "buyer", ignore = true)
    InvoiceEntity toEntity(InvoiceDTO source);

    @Mapping(target = "sellerId", source = "seller.id")
    @Mapping(target = "buyerId", source = "buyer.id")
    InvoiceDTO toDTO(InvoiceEntity source);
}





