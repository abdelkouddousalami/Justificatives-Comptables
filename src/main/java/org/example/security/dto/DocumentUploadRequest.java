package org.example.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.security.entity.TypeDocument;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DocumentUploadRequest {
    @NotBlank
    private String numeroPiece;
    
    @NotNull
    private TypeDocument type;
    
    private String categorieComptable;
    
    @NotNull
    private LocalDate datePiece;
    
    private BigDecimal montant;
    
    private String fournisseur;
}