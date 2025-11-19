package org.example.security.config;

import lombok.RequiredArgsConstructor;
import org.example.security.entity.Role;
import org.example.security.entity.Societe;
import org.example.security.entity.Utilisateur;
import org.example.security.repository.SocieteRepository;
import org.example.security.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final SocieteRepository societeRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create test company
        if (societeRepository.count() == 0) {
            Societe societe = new Societe();
            societe.setRaisonSociale("Test Company SARL");
            societe.setIce("123456789012345");
            societe.setAdresse("123 Rue Test, Casablanca");
            societe.setTelephone("0522123456");
            societe.setEmailContact("contact@testcompany.ma");
            societe = societeRepository.save(societe);

            // Create company user
            Utilisateur societeUser = new Utilisateur();
            societeUser.setEmail("societe@test.ma");
            societeUser.setMotDePasse(passwordEncoder.encode("password"));
            societeUser.setNomComplet("User Societe");
            societeUser.setRole(Role.SOCIETE);
            societeUser.setSociete(societe);
            utilisateurRepository.save(societeUser);

            // Create accountant user
            Utilisateur comptable = new Utilisateur();
            comptable.setEmail("comptable@alamane.ma");
            comptable.setMotDePasse(passwordEncoder.encode("password"));
            comptable.setNomComplet("Comptable Al Amane");
            comptable.setRole(Role.COMPTABLE);
            utilisateurRepository.save(comptable);
        }
    }
}