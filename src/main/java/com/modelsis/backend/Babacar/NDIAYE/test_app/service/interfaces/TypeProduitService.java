package com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces;

import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;

public interface TypeProduitService {
    /**
     * Save an productType
     *
     * @param dto the entity to save
     */
    Response<Object> create(TypeProduitRequestDTO dto);
}
