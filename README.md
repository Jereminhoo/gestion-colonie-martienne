# Gestion Colonie Martienne

Projet de développement d'une application **Java EE** pour la gestion administrative et logistique d'une colonie sur Mars.
Ce projet inclut une interface Web et une **API REST** complète permettant la gestion des membres d'équipage, des missions, des tâches et des stocks de ressources.

## Technologies Utilisées

* **Langage :** Java (Jakarta EE)
* **Architecture :** MVC (Model - DAO - Service - Resource)
* **API :** JAX-RS (RESTful Web Services)
* **Persistance :** JPA / Hibernate
* **Base de données :** MySQL (ou H2/PostgreSQL selon config)
* **Gestion de projet :** Maven, Git

---

##  Fonctionnalités Principales

###  Gestion des Membres (Équipage)
* Inscription des membres (Rôle par défaut : `MEMBRE`).
* Système de rôles (`CHEF` vs `MEMBRE`).
* **Sécurité :** Hachage des mots de passe et protection des données sensibles (le mot de passe n'est jamais renvoyé par l'API).
* **Règle métier :** Impossible de supprimer le membre ayant le rôle `CHEF`.

###  Gestion des Missions
* Planification des missions (Titre, Description, Dates).
* **Règle métier :** Cohérence temporelle (La date de fin ne peut pas être antérieure à la date de début).
* Suivi du statut (`PREVUE`, `EN_COURS`, `TERMINEE`).

###  Gestion des Tâches
* Attribution des tâches à un **Membre** et à une **Mission**.
* Architecture relationnelle (OneToMany / ManyToOne).
* Validation des contraintes (Une tâche doit obligatoirement avoir un responsable et une mission).

###  Gestion des Ressources
* Suivi des stocks de la colonie (Eau, Oxygène, Nourriture, Matériaux...).
* Ajout et suppression de ressources.
* Mise à jour des quantités disponibles.

---

##  Documentation de l'API REST

L'API est accessible via le chemin de base : `/api`
Les échanges de données se font au format **JSON**.

### 1. Membres (`/api/membres`)
| Méthode | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/membres` | Liste tous les membres (Mots de passe masqués). |
| `GET` | `/membres/{id}` | Récupère un membre par son ID. |
| `POST` | `/membres` | Inscrit un nouveau membre. |
| `PUT` | `/membres/{id}` | Met à jour le profil (Nom, Spécialité...). |
| `DELETE` | `/membres/{id}` | Supprime un membre (Sauf le Chef). |

### 2. Missions (`/api/missions`)
| Méthode | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/missions` | Liste toutes les missions. |
| `GET` | `/missions/{id}` | Détails d'une mission. |
| `GET` | `/missions/{id}/taches` | **Spécial :** Liste les tâches liées à cette mission. |
| `POST` | `/missions` | Crée une mission (Vérification des dates). |
| `PUT` | `/missions/{id}` | Modifie une mission. |
| `DELETE` | `/missions/{id}` | Supprime une mission. |

### 3. Tâches (`/api/taches`)
| Méthode | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/taches` | Liste toutes les tâches. |
| `POST` | `/taches` | Crée une tâche (nécessite ID mission + ID membre). |
| `PUT` | `/taches/{id}` | Met à jour une tâche (Statut...). |
| `DELETE` | `/taches/{id}` | Supprime une tâche. |

### 4. Ressources (`/api/ressources`)
| Méthode | URL | Description |
| :--- | :--- | :--- |
| `GET` | `/ressources` | Liste toutes les ressources disponibles. |
| `GET` | `/ressources/{id}` | Détails d'une ressource spécifique. |
| `POST` | `/ressources` | Ajoute une nouvelle ressource au stock. |
| `PUT` | `/ressources/{id}` | Met à jour une ressource (ex: quantité). |
| `DELETE` | `/ressources/{id}` | Supprime une ressource de l'inventaire. |

---

##  Installation et Lancement

1.  Cloner le dépôt :
    ```bash
    git clone [https://github.com/Jereminhoo/gestion-colonie-martienne.git]
    ```
2.  Configurer la base de données dans `src/main/resources/META-INF/persistence.xml`.
3.  Lancer le serveur d'application (ex: Wildfly / Glassfish).
4.  Accéder à l'API via `http://localhost:8080/gestion-colonie-martienne/api/...`.

---

##  Auteur

Projet réalisé dans le cadre du cours de Développement Java EE.
**[Muanza Jeremy]**