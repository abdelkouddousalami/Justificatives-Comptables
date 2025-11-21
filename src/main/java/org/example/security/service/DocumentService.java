package org.example.security.service;

import lombok.RequiredArgsConstructor;
import org.example.security.entity.*;
import org.example.security.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final FileStorageService fileStorageService;

    public Document uploadDocument(Document document, MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);
        document.setFichierPiece(fileName);
        document.setStatut(StatutDocument.EN_ATTENTE);
        return documentRepository.save(document);
    }

    public List<Document> getDocumentsBySociete(Societe societe) {
        return documentRepository.findBySociete(societe);
    }

    public List<Document> getPendingDocuments() {
        return documentRepository.findByStatut(StatutDocument.EN_ATTENTE);
    }

    public Document validateDocument(Long documentId, String commentaire) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setStatut(StatutDocument.VALIDE);
        document.setDateValidation(LocalDateTime.now());
        document.setCommentaireComptable(commentaire);

        return documentRepository.save(document);
    }

    public Document rejectDocument(Long documentId, String motif) {
        Document document = documentRepository.findById(documentId)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        document.setStatut(StatutDocument.REJETE);
        document.setDateValidation(LocalDateTime.now());
        document.setCommentaireComptable(motif);

        return documentRepository.save(document);
    }
}