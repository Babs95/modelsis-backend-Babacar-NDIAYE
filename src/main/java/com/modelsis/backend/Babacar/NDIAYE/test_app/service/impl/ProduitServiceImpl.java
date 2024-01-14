package com.modelsis.backend.Babacar.NDIAYE.test_app.service.impl;

import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.Produit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.ProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.TypeProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitUpdateRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.ProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.ProduitService;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper.ProduitMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProduitServiceImpl implements ProduitService {
    private final ProduitRepository produitRepository;
    private final TypeProduitRepository typeProduitRepository;
    private final ProduitMapper produitMapper;
    @Override
    public Response<Object> getProduits() {
        List<ProduitResponseDTO> produitResponseDTOList = produitRepository.findAll()
                .stream().map(produitMapper::toDto)
                .collect(Collectors.toList());

        if(produitResponseDTOList.isEmpty())
            return Response.notFound().setPayload(produitResponseDTOList)
                    .setSize(0)
                    .setMessage("Empty produits list !");

        return Response.ok().setPayload(produitResponseDTOList)
                .setSize(produitResponseDTOList.size())
                .setMessage("Liste des produits !");
    }

    @Override
    public Response<Object> create(ProduitRequestDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        Optional<TypeProduit> typeProduit = Optional.of(typeProduitRepository.findById(dto.getType_produit_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'id du type de produit n'existe pas!")));
        produit.setTypeProduit(typeProduit.get());

        try {
            produit = produitRepository.saveAndFlush(produit);
            log.info("Produit crée !");
            return Response.ok().setPayload(produitMapper.toDto(produit)).setMessage("Produit crée !");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response<Object> update(ProduitUpdateRequestDTO dto) {
        Optional<TypeProduit> typeProduit = Optional.of(typeProduitRepository.findById(dto.getType_produit_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "L'id du type de produit n'existe pas!")));

        Produit produit = produitRepository.findById(dto.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"L'id du produit n'existe pas!"));

        produit.setName(dto.getName());
        produit.setTypeProduit(typeProduit.get());

        try {
            produit = produitRepository.saveAndFlush(produit);
            log.info("Produit modifiée !");
            return Response.ok().setPayload(produitMapper.toDto(produit)).setMessage("Produit modifiée !");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
