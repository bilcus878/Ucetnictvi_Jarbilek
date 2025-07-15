package cz.itnetwork.dto.mapper;

import cz.itnetwork.dto.InvoiceDTO;
import cz.itnetwork.entity.InvoiceEntity;
import cz.itnetwork.entity.PersonEntity;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.11 (Eclipse Adoptium)"
)
@Component
public class InvoiceMapperImpl implements InvoiceMapper {

    @Autowired
    private PersonMapper personMapper;

    @Override
    public InvoiceEntity toEntity(InvoiceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        invoiceEntity.setId( dto.getId() );
        invoiceEntity.setInvoiceNumber( dto.getInvoiceNumber() );
        invoiceEntity.setIssued( dto.getIssued() );
        invoiceEntity.setDueDate( dto.getDueDate() );
        invoiceEntity.setProduct( dto.getProduct() );
        invoiceEntity.setPrice( dto.getPrice() );
        invoiceEntity.setVat( dto.getVat() );
        invoiceEntity.setNote( dto.getNote() );

        return invoiceEntity;
    }

    @Override
    public InvoiceDTO toDTO(InvoiceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setSellerId( entitySellerId( entity ) );
        invoiceDTO.setBuyerId( entityBuyerId( entity ) );
        invoiceDTO.setSeller( personMapper.toDTO( entity.getSeller() ) );
        invoiceDTO.setBuyer( personMapper.toDTO( entity.getBuyer() ) );
        invoiceDTO.setId( entity.getId() );
        invoiceDTO.setInvoiceNumber( entity.getInvoiceNumber() );
        invoiceDTO.setIssued( entity.getIssued() );
        invoiceDTO.setDueDate( entity.getDueDate() );
        invoiceDTO.setProduct( entity.getProduct() );
        invoiceDTO.setPrice( entity.getPrice() );
        invoiceDTO.setVat( entity.getVat() );
        invoiceDTO.setNote( entity.getNote() );

        return invoiceDTO;
    }

    private Long entitySellerId(InvoiceEntity invoiceEntity) {
        if ( invoiceEntity == null ) {
            return null;
        }
        PersonEntity seller = invoiceEntity.getSeller();
        if ( seller == null ) {
            return null;
        }
        Long id = seller.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityBuyerId(InvoiceEntity invoiceEntity) {
        if ( invoiceEntity == null ) {
            return null;
        }
        PersonEntity buyer = invoiceEntity.getBuyer();
        if ( buyer == null ) {
            return null;
        }
        Long id = buyer.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
