package org.example.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String numeroPiece;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TypeDocument type;
    
    @Column
    private String categorieComptable;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate datePiece;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal montant;
    
    @Column
    private String fournisseur;
    
    @NotBlank
    @Column(nullable = false)
    private String fichierPiece;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutDocument statut = StatutDocument.EN_ATTENTE;
    
    @Column
    private LocalDateTime dateValidation;
    
    @Column(length = 1000)
    private String commentaireComptable;
    
    @ManyToOne
    @JoinColumn(name = "societe_id", nullable = false)
    private Societe societe;
    
    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    @Column(nullable = false)
    private LocalDateTime dateModification = LocalDateTime.now();
    
    @PreUpdate
    public void preUpdate() {
        dateModification = LocalDateTime.now();
    }
}