package org.example.security.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.security.dto.DocumentUploadRequest;
import org.example.security.entity.Document;
import org.example.security.entity.Role;
import org.example.security.entity.Utilisateur;
import org.example.security.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<Document> uploadDocument(
            @Valid @ModelAttribute DocumentUploadRequest request,
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        Document document = new Document();
        document.setNumeroPiece(request.getNumeroPiece());
        document.setType(request.getType());
        document.setCategorieComptable(request.getCategorieComptable());
        document.setDatePiece(request.getDatePiece());
        document.setMontant(request.getMontant());
        document.setFournisseur(request.getFournisseur());
        document.setSociete(utilisateur.getSociete());

        Document savedDocument = documentService.uploadDocument(document, file);
        return ResponseEntity.ok(savedDocument);
    }

    @GetMapping("/my-documents")
    public ResponseEntity<List<Document>> getMyDocuments(Authentication authentication) {
        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();
        List<Document> documents = documentService.getDocumentsBySociete(utilisateur.getSociete());
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Document>> getPendingDocuments(Authentication authentication) {
        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        if (utilisateur.getRole() != Role.COMPTABLE) {
            return ResponseEntity.forbiddenBuild();
        }

        List<Document> documents = documentService.getPendingDocuments();
        return ResponseEntity.ok(documents);
    }

    @PostMapping("/validate/{id}")
    public ResponseEntity<Document> validateDocument(
            @PathVariable Long id,
            @RequestBody(required = false) Map<String, String> body,
            Authentication authentication) {

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        if (utilisateur.getRole() != Role.COMPTABLE) {
            return ResponseEntity.forbiddenBuild();
        }

        String commentaire = body != null ? body.get("commentaire") : null;
        Document document = documentService.validateDocument(id, commentaire);
        return ResponseEntity.ok(document);
    }

    @PostMapping("/reject/{id}")
    public ResponseEntity<Document> rejectDocument(
            @PathVariable Long id,
            @RequestBody Map<String, String> body,
            Authentication authentication) {

        Utilisateur utilisateur = (Utilisateur) authentication.getPrincipal();

        if (utilisateur.getRole() != Role.COMPTABLE) {
            return ResponseEntity.forbiddenBuild();
        }

        String motif = body.get("motif");
        if (motif == null || motif.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Document document = documentService.rejectDocument(id, motif);
        return ResponseEntity.ok(document);
    }
}