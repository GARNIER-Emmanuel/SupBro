# Supbro

Petit projet d’apprentissage pour enregistrer les informations de ses amis depuis une interface Angular, avec une API Spring Boot et une base PostgreSQL.

L’objectif principal est de pratiquer les tests de bout en bout avec Selenium sur un parcours simple : créer un ami, l’afficher et le retrouver après rechargement de la page.

## Stack prévue

- Java 21, Spring Boot et Spring Data JPA
- Angular
- PostgreSQL et Flyway pour les migrations
- JUnit et Mockito pour les tests unitaires
- Tests d’intégration avec PostgreSQL
- Selenium pour les tests de bout en bout
- GitHub Actions pour l’intégration continue

## Périmètre initial

- Formulaire de création d’un ami : prénom, nom et notes
- Enregistrement en base et affichage de la liste des amis
- Validation des données
- Automatisation des tests et des builds

## État du projet

Le socle Spring Boot, l’entité `Friend`, le repository, les DTO et le mapper sont présents. L’API REST, la configuration PostgreSQL, les migrations, l’interface Angular et les tests métier restent à ajouter.
