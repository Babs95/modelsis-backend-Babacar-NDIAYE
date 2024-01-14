package com.modelsis.backend.Babacar.NDIAYE.test_app.repos;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.Produit;
import com.modelsis.backend.Babacar.NDIAYE.test_app.domain.TypeProduit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProduitRepositoryTest {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private TypeProduitRepository typeProduitRepository;

    @Test
    public void GetAll_ReturnMoreThanOneProduit() {
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Smartphone")
                .dateCreated(OffsetDateTime.now())
                .build();
        typeProduitRepository.save(typeProduit);

        Produit produit1 = Produit.builder()
                .name("Iphone")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        Produit produit2 = Produit.builder()
                .name("Samsung Galaxy S23")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        produitRepository.save(produit1);
        produitRepository.save(produit2);

        List<Produit> produitList = produitRepository.findAll();

        Assertions.assertThat(produitList).isNotNull();
        Assertions.assertThat(produitList.size()).isEqualTo(2);
    }

    @Test
    public void testCreateReadDelete() {
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Smartphone")
                .dateCreated(OffsetDateTime.now())
                .build();
        typeProduitRepository.save(typeProduit);

        Produit produit = Produit.builder()
                .name("Iphone")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        produitRepository.save(produit);

        Iterable<Produit> produits = produitRepository.findAll();
        Assertions.assertThat(produits).extracting(Produit::getName).containsOnly("Iphone");

        produitRepository.deleteAll();
        Assertions.assertThat(produitRepository.findAll()).isEmpty();
    }

    @Test
    void itShouldFindProduitById() {
        // given
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Smartphone")
                .dateCreated(OffsetDateTime.now())
                .build();
        typeProduitRepository.save(typeProduit);

        Produit produit = Produit.builder()
                .name("Iphone")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        produitRepository.save(produit);

        // when
        Produit result = produitRepository.findById(produit.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        // then
        Assertions.assertThat(result).isEqualTo(produit);
    }

    @Test
    void itShouldUpdateProduit() {
        // given
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Laptop")
                .dateCreated(OffsetDateTime.now())
                .build();
        typeProduitRepository.save(typeProduit);

        Produit produit = Produit.builder()
                .name("MacBook Pro")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        produitRepository.save(produit);

        // when
        produit.setName("Updated MacBook Pro");
        produitRepository.save(produit);

        // then
        Produit updatedProduit = produitRepository.findById(produit.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        assertThat(updatedProduit.getName()).isEqualTo("Updated MacBook Pro");
    }

    @Test
    void itShouldDeleteProduit() {
        // given
        TypeProduit typeProduit = TypeProduit.builder()
                .name("Laptop")
                .dateCreated(OffsetDateTime.now())
                .build();
        typeProduitRepository.save(typeProduit);

        Produit produit = Produit.builder()
                .name("MacBook Pro")
                .dateCreated(OffsetDateTime.now())
                .typeProduit(typeProduit)
                .build();

        produitRepository.save(produit);
        // when
        produitRepository.deleteById(produit.getId());

        // then
        assertThat(produitRepository.findById(produit.getId())).isEmpty();
    }

}
