package com.modelsis.backend.Babacar.NDIAYE.test_app.service.impl;

import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.TypeProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.TypeProduitService;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper.TypeProduitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class TypeProduitServiceImpl implements TypeProduitService {
    private final TypeProduitRepository typeProduitRepository;
    private final TypeProduitMapper typeProduitMapper;
    @Override
    public Response<Object> create(TypeProduitRequestDTO dto) {
        TypeProduit typeProduit = typeProduitMapper.toEntity(dto);

        try {
            typeProduit = typeProduitRepository.save(typeProduit);
            log.info("TypeProduit crée !");
            return Response.ok().setPayload(typeProduitMapper.toDto(typeProduit)).setMessage("TypeProduit crée !");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
