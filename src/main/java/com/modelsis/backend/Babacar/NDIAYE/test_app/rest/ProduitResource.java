package com.modelsis.backend.Babacar.NDIAYE.test_app.rest;
import com.modelsis.backend.Babacar.NDIAYE.test_app.config.AppConstants;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitUpdateRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping(value = AppConstants.MAIN_ENDPOINT_V1 + "/product", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ProduitResource {
    private final ProduitService produitService;

    @GetMapping()
    public Response<Object> getProduits() {
        return produitService.getProduits();
    }

    @PostMapping()
    public Response<Object> create(@RequestBody @Valid final ProduitRequestDTO dto) {
        return produitService.create(dto);
    }

    @PutMapping()
    public Response<Object> update(@RequestBody @Valid final ProduitUpdateRequestDTO dto) {
        return produitService.update(dto);
    }
}
