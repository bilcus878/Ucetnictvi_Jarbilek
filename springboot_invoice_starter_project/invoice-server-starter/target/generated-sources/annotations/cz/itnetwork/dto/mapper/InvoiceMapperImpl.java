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
    public InvoiceEntity toEntity(InvoiceDTO source) {
        if ( source == null ) {
            return null;
        }

        InvoiceEntity invoiceEntity = new InvoiceEntity();

        if ( source.getId() != null ) {
            invoiceEntity.setId( source.getId() );
        }
        invoiceEntity.setInvoiceNumber( source.getInvoiceNumber() );
        invoiceEntity.setIssued( source.getIssued() );
        invoiceEntity.setDueDate( source.getDueDate() );
        invoiceEntity.setProduct( source.getProduct() );
        invoiceEntity.setPrice( source.getPrice() );
        invoiceEntity.setVat( source.getVat() );
        invoiceEntity.setNote( source.getNote() );

        return invoiceEntity;
    }

    @Override
    public InvoiceDTO toDTO(InvoiceEntity source) {
        if ( source == null ) {
            return null;
        }

        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setSellerId( sourceSellerId( source ) );
        invoiceDTO.setBuyerId( sourceBuyerId( source ) );
        invoiceDTO.setId( source.getId() );
        invoiceDTO.setInvoiceNumber( source.getInvoiceNumber() );
        invoiceDTO.setIssued( source.getIssued() );
        invoiceDTO.setDueDate( source.getDueDate() );
        invoiceDTO.setProduct( source.getProduct() );
        invoiceDTO.setPrice( source.getPrice() );
        invoiceDTO.setVat( source.getVat() );
        invoiceDTO.setNote( source.getNote() );
        invoiceDTO.setSeller( personMapper.toDTO( source.getSeller() ) );
        invoiceDTO.setBuyer( personMapper.toDTO( source.getBuyer() ) );

        return invoiceDTO;
    }

    private Long sourceSellerId(InvoiceEntity invoiceEntity) {
        if ( invoiceEntity == null ) {
            return null;
        }
        PersonEntity seller = invoiceEntity.getSeller();
        if ( seller == null ) {
            return null;
        }
        long id = seller.getId();
        return id;
    }

    private Long sourceBuyerId(InvoiceEntity invoiceEntity) {
        if ( invoiceEntity == null ) {
            return null;
        }
        PersonEntity buyer = invoiceEntity.getBuyer();
        if ( buyer == null ) {
            return null;
        }
        long id = buyer.getId();
        return id;
    }
}
