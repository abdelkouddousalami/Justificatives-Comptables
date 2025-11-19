package org.example.security.repository;

import org.example.security.entity.Role;
import org.example.security.entity.Utilisateur;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UtilisateurRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Test
    void testFindByEmail() {
        // Given
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail("test@test.com");
        utilisateur.setMotDePasse("password");
        utilisateur.setNomComplet("Test User");
        utilisateur.setRole(Role.COMPTABLE);
        entityManager.persistAndFlush(utilisateur);

        // When
        Optional<Utilisateur> found = utilisateurRepository.findByEmail("test@test.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getNomComplet()).isEqualTo("Test User");
        assertThat(found.get().getRole()).isEqualTo(Role.COMPTABLE);
    }

    @Test
    void testFindByEmailNotFound() {
        // When
        Optional<Utilisateur> found = utilisateurRepository.findByEmail("nonexistent@test.com");

        // Then
        assertThat(found).isEmpty();
    }
}