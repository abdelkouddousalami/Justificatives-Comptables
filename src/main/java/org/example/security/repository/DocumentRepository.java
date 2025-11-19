package org.example.security.repository;

import org.example.security.entity.Document;
import org.example.security.entity.Societe;
import org.example.security.entity.StatutDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findBySociete(Societe societe);
    List<Document> findByStatut(StatutDocument statut);
    List<Document> findBySocieteAndStatut(Societe societe, StatutDocument statut);
}