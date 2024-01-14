package com.modelsis.backend.Babacar.NDIAYE.test_app.repos;

import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.OffsetDateTime;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TypeProduitRepositoryTest {
    @Autowired
    private TypeProduitRepository typeProduitRepository;

    @Test
    public void testCreateReadDelete() {
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Smartphone")
                .dateCreated(OffsetDateTime.now())
                .build();

        typeProduitRepository.save(typeProduit);

        Iterable<TypeProduit> categories = typeProduitRepository.findAll();
        Assertions.assertThat(categories).extracting(TypeProduit::getName).containsOnly("Smartphone");

        typeProduitRepository.deleteAll();
        Assertions.assertThat(typeProduitRepository.findAll()).isEmpty();
    }
}
