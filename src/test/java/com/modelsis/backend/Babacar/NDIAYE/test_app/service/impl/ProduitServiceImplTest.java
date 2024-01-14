package com.modelsis.backend.Babacar.NDIAYE.test_app.service.impl;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.Produit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.ProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.TypeProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitUpdateRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.ProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.TypeProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper.ProduitMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProduitServiceImplTest {
    @InjectMocks
    private ProduitServiceImpl produitService;
    @Mock
    private ProduitRepository produitRepository;
    @Mock
    private TypeProduitRepository typeProduitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @Test
    void testGetProduits() {
        // Given
        List<Produit> produitList = Arrays.asList(
                Produit.builder().id(104L).name("Asus Rog Strix")
                        .typeProduit(TypeProduit.builder().id(100L).name("Ordi").build()).build(),
                Produit.builder().id(104L).name("Iphone 15 Pro")
                        .typeProduit(TypeProduit.builder().id(100L).name("Telephone").build()).build(),
                Produit.builder().id(104L).name("Gel Moussant Cerave")
                        .typeProduit(TypeProduit.builder().id(100L).name("Cosmetique").build()).build()
        );

        List<ProduitResponseDTO> expectedProduitDTOs = Arrays.asList(
                ProduitResponseDTO.builder().id(104L).name("Asus Rog Strix")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Ordi").build()).build(),
                ProduitResponseDTO.builder().id(104L).name("Iphone 15 Pro")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Telephone").build()).build(),
                ProduitResponseDTO.builder().id(104L).name("Gel Moussant Cerave")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Cosmetique").build()).build()
        );

        // Mocking behavior
        when(produitRepository.findAll()).thenReturn(produitList);
        when(produitMapper.toDto(Mockito.any(Produit.class)))
                .thenReturn(expectedProduitDTOs.get(0))
                .thenReturn(expectedProduitDTOs.get(1))
                .thenReturn(expectedProduitDTOs.get(2));

        // When
        Response<Object> response = produitService.getProduits();

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getPayload()).isEqualTo(expectedProduitDTOs);
        assertThat(response.getSize()).isEqualTo(3);
        assertThat(response.getMessage()).isEqualTo("Liste des produits !");
    }

    @Test
    void testCreate() {
        // Given
        Long typeProduitId = 103L;

        ProduitRequestDTO produitRequestDTO = ProduitRequestDTO.builder()
                .name("Gel Moussant Cerave")
                .type_produit_id(typeProduitId)
                .build();

        TypeProduit typeProduitEntity = TypeProduit.builder().name("Cosmetique").build();
        TypeProduitResponseDTO expectedTypeProduitDTO = TypeProduitResponseDTO.builder()
                .id(typeProduitId)
                .name("Cosmetique")
                .build();

        Produit produitEntity = Produit.builder()
                .name("Gel Moussant Cerave")
                .typeProduit(typeProduitEntity)
                .build();


        ProduitResponseDTO expectedResponseDTO = ProduitResponseDTO.builder()
                .id(106L)
                .name("Gel Moussant Cerave")
                .typeProduit(expectedTypeProduitDTO)
                .build();

        // Mocking behavior
        when(produitMapper.toEntity(any(ProduitRequestDTO.class))).thenReturn(produitEntity);
        when(typeProduitRepository.findById(typeProduitId)).thenReturn(Optional.of(typeProduitEntity));
        when(produitRepository.saveAndFlush(any(Produit.class))).thenReturn(produitEntity);
        when(produitMapper.toDto(any(Produit.class))).thenReturn(expectedResponseDTO);

        // When
        Response<Object> response = produitService.create(produitRequestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Produit crée !");
    }

    @Test
    void testUpdate() {
        // Given
        Long typeProduitId = 1L;
        Long produitId = 1L;

        ProduitUpdateRequestDTO produitUpdateRequestDTO = ProduitUpdateRequestDTO.builder()
                .id(produitId)
                .name("Updated Product")
                .type_produit_id(typeProduitId)
                .build();

        TypeProduit typeProduitEntity = TypeProduit.builder().name("Updated ProductType").build();
        TypeProduitResponseDTO expectedTypeProduitDTO = TypeProduitResponseDTO.builder().id(typeProduitId).build();

        Produit produitEntity = Produit.builder()
                .id(produitId)
                .name("Original Product")
                .typeProduit(typeProduitEntity)
                .dateCreated(OffsetDateTime.now())
                .build();

        ProduitResponseDTO expectedResponseDTO = ProduitResponseDTO.builder()
                .id(produitId)
                .name("Updated Product")
                .typeProduit(expectedTypeProduitDTO)
                .dateCreated(produitEntity.getDateCreated().toString())
                .build();

        // Mocking behavior
        when(typeProduitRepository.findById(typeProduitId)).thenReturn(Optional.of(typeProduitEntity));
        when(produitRepository.findById(produitId)).thenReturn(Optional.of(produitEntity));
        when(produitRepository.saveAndFlush(any(Produit.class))).thenReturn(produitEntity);
        when(produitMapper.toDto(any(Produit.class))).thenReturn(expectedResponseDTO);

        // When
        Response<Object> response = produitService.update(produitUpdateRequestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("Produit modifiée !");
    }
}
