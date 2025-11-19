# Cabinet Comptable Al Amane - Document Management System

## Description
Application de gestion documentaire pour le Cabinet Comptable Al Amane permettant la centralisation et la sécurisation des documents comptables de 35 sociétés clientes.

## Fonctionnalités
- **Authentification JWT** avec expiration 24h
- **Gestion des rôles** : SOCIETE et COMPTABLE
- **Upload de documents** (PDF, JPG, PNG jusqu'à 10MB)
- **Validation/Rejet** de documents par les comptables
- **Consultation** des documents par exercice comptable
- **Conformité légale** avec la réglementation marocaine

## Technologies
- **Backend**: Spring Boot 3.4.0
- **Sécurité**: Spring Security 6 + JWT
- **Base de données**: H2 (en mémoire)
- **Tests**: JUnit 5 + Mockito
- **Containerisation**: Docker

## Installation et Exécution

### Prérequis
- Java 17+
- Maven 3.6+
- Docker (optionnel)

### Lancement local
```bash
mvn clean install
mvn spring-boot:run
```

### Avec Docker
```bash
mvn clean package -DskipTests
docker build -t cabinet-comptable .
docker run -p 8080:8080 cabinet-comptable
```

## API Endpoints

### Authentification
- `POST /api/auth/login` - Connexion utilisateur

### Documents (Société)
- `POST /api/documents/upload` - Upload d'un document
- `GET /api/documents/my-documents` - Consultation des documents

### Documents (Comptable)
- `GET /api/documents/pending` - Documents en attente
- `POST /api/documents/validate/{id}` - Valider un document
- `POST /api/documents/reject/{id}` - Rejeter un document

## Utilisateurs de test
- **Société**: `societe@test.ma` / `password`
- **Comptable**: `comptable@alamane.ma` / `password`

## Base de données H2
Console accessible sur: http://localhost:8080/h2-console
- URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (vide)

## Tests
```bash
mvn test
```

## CI/CD
Le projet inclut un workflow GitHub Actions pour:
- Build automatique
- Tests unitaires
- Construction d'image Docker
- Push vers Docker Hub

### Configuration des secrets GitHub
- `DOCKER_USERNAME`: Nom d'utilisateur Docker Hub
- `DOCKER_PASSWORD`: Mot de passe Docker Hub