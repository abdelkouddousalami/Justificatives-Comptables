package org.example.security.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "societes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Societe {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String raisonSociale;
    
    @NotBlank
    @Column(unique = true, nullable = false)
    private String ice;
    
    @NotBlank
    @Column(nullable = false)
    private String adresse;
    
    @NotBlank
    @Column(nullable = false)
    private String telephone;
    
    @Email
    @NotBlank
    @Column(nullable = false)
    private String emailContact;
    
    @Column(nullable = false)
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL)
    private List<Utilisateur> utilisateurs;
    
    @OneToMany(mappedBy = "societe", cascade = CascadeType.ALL)
    private List<Document> documents;
}