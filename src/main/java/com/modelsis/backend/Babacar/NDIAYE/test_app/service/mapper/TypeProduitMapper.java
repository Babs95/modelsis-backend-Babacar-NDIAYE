package com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper;

import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.TypeProduitResponseDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TypeProduitMapper {
    TypeProduit toEntity(TypeProduitRequestDTO dto);
    @Mapping(target = "dateCreated", expression = "java(offsetDateToStringDate(typeProduit.getDateCreated()))")
    TypeProduitResponseDTO toDto(TypeProduit typeProduit);
    default String offsetDateToStringDate(OffsetDateTime date) {
        if(Objects.nonNull(date)) {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("dd/MM/yyyy");
            return date.format(formatter);
        }
        return "";
    }
}
