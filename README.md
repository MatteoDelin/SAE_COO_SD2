# SAE COO SD2 — Application de Gestion de Réservations

> Projet universitaire réalisé dans le cadre de la SAE (Situation d'Apprentissage et d'Évaluation) de **Conception Orientée Objet** — BUT Science des Données, Semestre 3.

---

## Description

Application Java avec **interface graphique Swing** permettant de gérer des utilisateurs, des ressources et des réservations. Les données peuvent être importées et exportées au format CSV.

---

## Fonctionnalités

| # | Fonctionnalité | Description |
|---|---------------|-------------|
| 1 | **Gestion des utilisateurs** | Créer, modifier, afficher et supprimer des utilisateurs |
| 2 | **Gestion des ressources** | Créer, modifier, afficher et supprimer des ressources |
| 3 | **Gestion des réservations** | Créer, modifier, afficher et supprimer des réservations |
| 4 | **Import CSV** | Chargement des données depuis un fichier CSV fourni |
| 5 | **Export CSV** | Export des réservations mises à jour vers un fichier CSV |

---

## Technologies utilisées

| Technologie | Rôle |
|------------|------|
| **Java** | Langage principal |
| **Swing** | Interface graphique |
| **MigLayout** | Gestionnaire de mise en page avancé pour Swing |
| **JGoodies (Forms & Common)** | Composants et layouts UI supplémentaires |
| **JCalendar** | Sélecteur de dates dans l'interface |
| **CSV** | Format d'import/export des données |

---

## Structure du projet

```
SAE_COO_SD2/
├── GMI/
│   ├── src/
│   │   ├── PaneReservations/
│   │   │   ├── CreationReservations.java
│   │   │   ├── DeleteReservations.java
│   │   │   ├── ModifieReservations.java
│   │   │   └── PrintReservations.java
│   │   ├── PaneRessources/
│   │   │   ├── CreationRessources.java
│   │   │   ├── DeleteRessources.java
│   │   │   ├── ModifieRessources.java
│   │   │   └── PrintRessources.java
│   │   ├── PaneUser/
│   │   │   ├── CreationUser.java
│   │   │   ├── DeleteUser.java
│   │   │   ├── ModifieUser.java
│   │   │   └── PrintUser.java
│   │   ├── CSV.java                  # Gestion import/export CSV
│   │   ├── MainWindow.java           # Fenêtre principale de l'application
│   │   ├── Reservations.java         # Entité métier Réservation
│   │   ├── Ressources.java           # Entité métier Ressource
│   │   └── Utilisateur.java          # Entité métier Utilisateur
│   ├── com.jcalendar-1.4.jar
│   ├── com.jgoodies.common_1.8.1.jar
│   ├── com.jgoodies.forms_1.9.0.jar
│   ├── com.miglayout.core_11.4.2.jar
│   ├── com.miglayout.swing_11.4.2.jar
│   ├── .classpath                    # Configuration Eclipse
│   └── .project                     # Configuration Eclipse
├── DiagrammeClasse.loo               # Diagramme de classes UML
├── Énoncé - Projet COO.pdf           # Sujet du projet
├── extraction-2021-2022-anonym.csv   # Données anonymisées (import)
└── README.md
```

---

## Lancer le projet

### Prérequis

- **Java** 11 ou supérieur
- **Eclipse IDE** (recommandé — fichiers `.classpath` et `.project` inclus)  
  ou tout autre IDE Java (IntelliJ IDEA, VS Code)

### Avec Eclipse

1. `File` → `Import` → `Existing Projects into Workspace`
2. Sélectionner le dossier `GMI`
3. Vérifier que les `.jar` sont bien dans le build path
4. Lancer la classe `MainWindow`

### En ligne de commande

```bash
# Cloner le dépôt
git clone https://github.com/MatteoDelin/SAE_COO_SD2.git
cd SAE_COO_SD2/GMI

# Compiler en incluant les librairies
javac -cp "lib/*" -d out src/**/*.java

# Lancer l'application
java -cp "out:lib/*" MainWindow
```

> Sur Windows, remplacer `:` par `;` dans le classpath.

---

## Données

Le fichier `extraction-2021-2022-anonym.csv` contient des données académiques anonymisées (année 2021-2022) servant de source initiale. L'application permet ensuite d'**exporter les réservations modifiées** dans un nouveau fichier CSV.

---

## Conception

Le diagramme de classes (`DiagrammeClasse.loo`) documente l'architecture orientée objet du projet :

- Entités métier : `Utilisateur`, `Ressources`, `Reservations`
- Panels dédiés par entité : `PaneUser`, `PaneRessources`, `PaneReservations`
- Chaque panel regroupe les vues de création, modification, affichage et suppression
- Interface graphique construite avec Swing + MigLayout
- Couche de persistance CSV via `CSV.java`

---

## Contexte académique

| Élément | Détail |
|--------|--------|
| **Formation** | BUT Science des Données |
| **Matière** | Conception Orientée Objet (COO) |
| **Semestre** | S3 |
| **Type** | SAE (Situation d'Apprentissage et d'Évaluation) |
| **Livrables** | Code source commenté · Documentation · Support oral |
| **Évaluation** | Oral + écrit |

---

