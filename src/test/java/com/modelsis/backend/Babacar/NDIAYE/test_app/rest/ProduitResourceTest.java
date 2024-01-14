package com.modelsis.backend.Babacar.NDIAYE.test_app.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.ProduitUpdateRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.ProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.TypeProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.ProduitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProduitResource.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProduitResourceTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    ProduitService produitService;

    @Test
    void getProduits_ShouldReturnListOfProduits() throws Exception {

        List<ProduitResponseDTO> expectedProduitDTOs = Arrays.asList(
                ProduitResponseDTO.builder().id(104L).name("Asus Rog Strix")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Ordi").build()).build(),
                ProduitResponseDTO.builder().id(104L).name("Iphone 15 Pro")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Telephone").build()).build(),
                ProduitResponseDTO.builder().id(104L).name("Gel Moussant Cerave")
                        .typeProduit(TypeProduitResponseDTO.builder().id(100L).name("Cosmetique").build()).build()
        );

        // Mocking the service behavior
        when(produitService.getProduits())
                .thenReturn(Response.ok().setPayload(expectedProduitDTOs).setMessage("Liste des produits !"));

        // Performing the request and asserting the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/product"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("Liste des produits !"))
                .andExpect(jsonPath("$.payload").isArray())
                .andExpect(jsonPath("$.payload.length()").value(3))
                .andExpect(jsonPath("$.payload[0].id").value(104L))
                .andExpect(jsonPath("$.payload[0].name").value("Asus Rog Strix"))
                .andExpect(jsonPath("$.payload[0].typeProduit.name").value("Ordi"))
                .andReturn();
    }

    @Test
    void testCreateProduit() throws Exception {
        // Given
        Long typeProduitId = 103L;

        ProduitRequestDTO produitRequestDTO = ProduitRequestDTO.builder()
                .name("Gel Moussant Cerave")
                .type_produit_id(typeProduitId)
                .build();

        TypeProduitResponseDTO expectedTypeProduitDTO = TypeProduitResponseDTO.builder()
                .id(typeProduitId)
                .name("Cosmetique")
                .build();


        ProduitResponseDTO createdProduit = ProduitResponseDTO.builder()
                .id(106L)
                .name("Gel Moussant Cerave")
                .typeProduit(expectedTypeProduitDTO)
                .build();

        when(produitService.create(produitRequestDTO))
                .thenReturn(Response.ok().setPayload(createdProduit).setMessage("Produit créé !"));

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produitRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.payload.id").value(106L))
                .andExpect(jsonPath("$.payload.name").value("Gel Moussant Cerave"))
                .andExpect(jsonPath("$.payload.typeProduit.id").value(typeProduitId))
                .andExpect(jsonPath("$.payload.typeProduit.name").value("Cosmetique"))
                .andExpect(jsonPath("$.message").value("Produit créé !"))
                .andReturn();
    }

    @Test
    void testUpdateProduit() throws Exception {
        // Given
        Long typeProduitId = 100L;
        Long produitId = 101L;

        ProduitUpdateRequestDTO produitUpdateRequestDTO = ProduitUpdateRequestDTO.builder()
                .id(produitId)
                .name("Iphone 15 Pro Updated")
                .type_produit_id(typeProduitId)
                .build();

        TypeProduitResponseDTO expecteTypeProduitDTO = TypeProduitResponseDTO.builder().id(typeProduitId).build();

        ProduitResponseDTO updatedProduit = ProduitResponseDTO.builder()
                .id(produitId)
                .name("Iphone 15 Pro Updated")
                .typeProduit(expecteTypeProduitDTO)
                .dateCreated(OffsetDateTime.now().toString())
                .build();

        when(produitService.update(produitUpdateRequestDTO))
                .thenReturn(Response.ok().setPayload(updatedProduit).setMessage("Produit modifié !"));

        // When and Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(produitUpdateRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.payload.id").value(produitId))
                .andExpect(jsonPath("$.payload.name").value("Iphone 15 Pro Updated"))
                .andExpect(jsonPath("$.payload.typeProduit.id").value(typeProduitId))
                .andExpect(jsonPath("$.message").value("Produit modifié !"))
                .andReturn();
    }
}
