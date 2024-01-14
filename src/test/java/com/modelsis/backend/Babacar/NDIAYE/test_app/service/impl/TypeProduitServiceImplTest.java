package com.modelsis.backend.Babacar.NDIAYE.test_app.service.impl;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.repos.TypeProduitRepository;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.request.TypeProduitRequestDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.TypeProduitResponseDTO;
import com.modelsis.backend.Babacar.NDIAYE.test_app.rest.response.common.Response;
import com.modelsis.backend.Babacar.NDIAYE.test_app.service.mapper.TypeProduitMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeProduitServiceImplTest {
    @InjectMocks
    private TypeProduitServiceImpl typeProduitService;

    @Mock
    private TypeProduitRepository typeProduitRepository;

    @Mock
    private TypeProduitMapper typeProduitMapper;

    @Test
    void testCreate() {
        // Given
        TypeProduitRequestDTO typeProduitRequestDTO = TypeProduitRequestDTO.builder()
                .name("Smartphone")
                .build();
        TypeProduit typeProduitEntity = TypeProduit.builder().name("Smartphone").build();
        TypeProduit savedTypeProduitEntity = TypeProduit.builder().id(1L).name("Smartphone").build();
        TypeProduitResponseDTO expectedResponseDTO = TypeProduitResponseDTO.builder().id(1L).name("Smartphone").build();

        // Mocking behavior
        when(typeProduitMapper.toEntity(any(TypeProduitRequestDTO.class))).thenReturn(typeProduitEntity);
        when(typeProduitRepository.save(any(TypeProduit.class))).thenReturn(savedTypeProduitEntity);
        when(typeProduitMapper.toDto(any(TypeProduit.class))).thenReturn(expectedResponseDTO);

        // When
        Response<Object> response = typeProduitService.create(typeProduitRequestDTO);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK);
        assertThat(response.getMessage()).isEqualTo("TypeProduit crée !");
    }

    @Test
    void testCreateWithException() {
        // Given
        TypeProduitRequestDTO typeProduitRequestDTO = TypeProduitRequestDTO.builder()
                .name("Smartphone")
                .build();
        TypeProduit typeProduitEntity = TypeProduit.builder().name("Smartphone").build();

        // Mocking behavior to simulate an exception during save
        when(typeProduitMapper.toEntity(any(TypeProduitRequestDTO.class))).thenReturn(typeProduitEntity);
        when(typeProduitRepository.save(any(TypeProduit.class))).thenThrow(new RuntimeException("Simulated exception"));

        // When and Then (using assertThrows to verify the exception is thrown)
        assertThrows(ResponseStatusException.class, () -> typeProduitService.create(typeProduitRequestDTO));
    }
}
