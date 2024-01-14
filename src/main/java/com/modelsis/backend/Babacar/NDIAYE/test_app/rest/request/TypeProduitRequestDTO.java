package com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeProduitRequestDTO {
    @NotEmpty
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z_.-À-ÖØ-öø-ÿ\\s]*$",
            message = "allowed only letters, ‘-‘, ‘_’, accented characters, and whitespace")
    private String name;
}
