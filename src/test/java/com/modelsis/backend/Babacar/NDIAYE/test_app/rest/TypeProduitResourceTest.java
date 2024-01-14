package com.modelsis.backend.Babacar.NDIAYE.test_app.rest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.TypeProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.interfaces.TypeProduitService;
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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TypeProduitResource.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class TypeProduitResourceTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TypeProduitService typeProduitService;

    @Test
    void createProductType_ShouldReturnCreatedProductType() throws Exception {
        TypeProduitRequestDTO requestDTO = new TypeProduitRequestDTO();
        requestDTO.setName("Cosmetique");

        TypeProduitResponseDTO expectedResponseDTO = TypeProduitResponseDTO.builder()
                .id(1L)
                .name("Cosmetique")
                .build();

        // Mocking the service behavior
        when(typeProduitService.create(any(TypeProduitRequestDTO.class)))
                .thenReturn(Response.ok().setPayload(expectedResponseDTO).setMessage("TypeProduit crée !"));

        // Performing the request and asserting the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/productType")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.status").value("OK"))
                .andExpect(jsonPath("$.message").value("TypeProduit crée !"))
                .andExpect(jsonPath("$.payload").isNotEmpty())
                .andExpect(jsonPath("$.payload.id").value(1L))
                .andExpect(jsonPath("$.payload.name").value("Cosmetique"))
                .andReturn();
    }
}
