package com.modelsis.backend.Babacar.NDIAYE.test_app.rest;
import com.modelsis.backend.Babacar.NDIAYE.test_app.config.AppConstants;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.TypeProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = AppConstants.MAIN_ENDPOINT_V1 + "/productType", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TypeProduitResource {
    private final TypeProduitService typeProduitService;
    @PostMapping()
    public Response<Object> create(@RequestBody @Valid final TypeProduitRequestDTO dto) {
        return typeProduitService.create(dto);
    }
}
