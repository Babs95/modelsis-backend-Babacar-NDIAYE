package com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces;

import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitUpdateRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;

public interface ProduitService {
    /**
     * List of produits
     * @return list of produits entity
     */
    Response<Object> getProduits();

    /**
     * Save an produit
     *
     * @param dto the entity to save
     */
    Response<Object> create(ProduitRequestDTO dto);

    /**
     * Update an produit
     *
     * @param dto the entity to update
     */
    Response<Object> update(ProduitUpdateRequestDTO dto);

}
