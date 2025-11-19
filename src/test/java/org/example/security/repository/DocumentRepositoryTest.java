package org.example.security.repository;

import org.example.security.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class DocumentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DocumentRepository documentRepository;

    @Test
    void testFindBySociete() {
        // Given
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Company");
        societe.setIce("123456789");
        societe.setAdresse("Test Address");
        societe.setTelephone("123456789");
        societe.setEmailContact("test@test.com");
        societe = entityManager.persistAndFlush(societe);

        Document document = new Document();
        document.setNumeroPiece("FAC001");
        document.setType(TypeDocument.FACTURE_ACHAT);
        document.setDatePiece(LocalDate.now());
        document.setMontant(new BigDecimal("1000.00"));
        document.setFichierPiece("test.pdf");
        document.setSociete(societe);
        entityManager.persistAndFlush(document);

        // When
        List<Document> documents = documentRepository.findBySociete(societe);

        // Then
        assertThat(documents).hasSize(1);
        assertThat(documents.get(0).getNumeroPiece()).isEqualTo("FAC001");
    }

    @Test
    void testFindByStatut() {
        // Given
        Societe societe = new Societe();
        societe.setRaisonSociale("Test Company");
        societe.setIce("123456789");
        societe.setAdresse("Test Address");
        societe.setTelephone("123456789");
        societe.setEmailContact("test@test.com");
        societe = entityManager.persistAndFlush(societe);

        Document document = new Document();
        document.setNumeroPiece("FAC001");
        document.setType(TypeDocument.FACTURE_ACHAT);
        document.setDatePiece(LocalDate.now());
        document.setMontant(new BigDecimal("1000.00"));
        document.setFichierPiece("test.pdf");
        document.setStatut(StatutDocument.EN_ATTENTE);
        document.setSociete(societe);
        entityManager.persistAndFlush(document);

        // When
        List<Document> documents = documentRepository.findByStatut(StatutDocument.EN_ATTENTE);

        // Then
        assertThat(documents).hasSize(1);
        assertThat(documents.get(0).getStatut()).isEqualTo(StatutDocument.EN_ATTENTE);
    }
}