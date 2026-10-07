# Supbro

Petit projet personnel réalisé par curiosité pour découvrir la mise en place de **Selenium**, comprendre son fonctionnement et essayer son exécution dans une CI GitHub Actions.

Je débute avec Selenium et la CI : ce dépôt est un exercice d’apprentissage, avec un périmètre volontairement réduit, plutôt qu’une application destinée à la production.

## Parcours et technologies

L’application permet de créer un ami (prénom, nom, notes), de l’enregistrer et de consulter la liste. Le test Selenium reproduit ce parcours dans Chrome, puis recharge la page pour vérifier la persistance.

- **Frontend :** Angular 21.
- **Backend :** Java 21, Spring Boot, Spring Data JPA.
- **Base :** PostgreSQL 17, Docker Compose, migrations Flyway.
- **Tests :** JUnit, Mockito, AssertJ, Testcontainers et Selenium.
- **CI :** GitHub Actions, configuré pour les TU/TI, le build Angular et les E2E sur push et pull request.

## Lancer en local

Prérequis : Java 21, Node 24, Docker Desktop démarré et Chrome pour Selenium. Commandes pour Bash / Git Bash, depuis la racine.

```bash
cp .env.example .env
docker compose up -d --wait
export DB_URL='jdbc:postgresql://localhost:5432/supbro'
export DB_USER='supbro'
export DB_PASSWORD='local-dev-only'
bash ./mvnw spring-boot:run
```

Adapter les variables si les valeurs de `.env` sont modifiées. Dans un autre terminal :

```bash
cd frontend
npm ci
npm start
```

Ouvrir **http://localhost:4200**. Le proxy Angular transmet les appels API à Spring sur le port 8080.

## Vérifications

TU et TI API (Docker requis) :

```bash
bash ./mvnw clean verify
```

Build Angular, depuis `frontend/` :

```bash
npm run build
```

Pour Selenium, arrêter le backend de développement, puis le relancer sur la base E2E dédiée :

```bash
docker compose -p supbro-e2e -f compose.e2e.yaml up -d --wait
export DB_URL='jdbc:postgresql://localhost:5434/supbro_e2e'
export DB_USER='supbro'
export DB_PASSWORD='local-e2e-only'
bash ./mvnw spring-boot:run
```

Garder Angular lancé et exécuter depuis un autre terminal à la racine :

```bash
bash ./mvnw -Pe2e -Dheadless=true verify
```

Sur GitHub, le job `e2e` démarre cet environnement automatiquement. Ses rapports, logs et éventuelles captures sont disponibles dans l’artefact `selenium-e2e-results`.

Les identifiants ci-dessus sont uniquement destinés au développement local. Ne pas versionner `.env`.

## Documentation

[Workflow CI](.github/workflows/ci.yml)
