package com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProduitResponseDTO {
    private Long id;
    private String name;
    private String dateCreated;

    private TypeProduitResponseDTO typeProduit;
}
