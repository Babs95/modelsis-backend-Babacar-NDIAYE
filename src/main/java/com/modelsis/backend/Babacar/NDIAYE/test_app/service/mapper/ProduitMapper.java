package com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.Produit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.ProduitResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Mapper(componentModel = "spring",
        uses = {TypeProduitMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProduitMapper {
    Produit toEntity(ProduitRequestDTO dto);
    @Mapping(target = "dateCreated", expression = "java(offsetDateToStringDate(produit.getDateCreated()))")
    ProduitResponseDTO toDto(Produit produit);

    default String offsetDateToStringDate(OffsetDateTime date) {
        if(Objects.nonNull(date)) {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }
        return "";
    }
}
