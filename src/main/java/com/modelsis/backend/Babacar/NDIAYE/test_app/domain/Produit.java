package com.modelsis.backend.Babacar.NDIAYE.test_app.domain;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Table(name = Produit.TABLE_NAME, schema = Produit.SCHEMA)
@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Produit {
    public static final String TABLE_NAME = "produit";
    public static final String SCHEMA = "public";

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @ManyToOne
    @JoinColumn(name = "type_produit_id", nullable = false)
    private TypeProduit typeProduit;
}
