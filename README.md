# Projet Messagerie Java JDBC

Ce projet démontre l'utilisation de JDBC pour interagir avec une base de données MySQL dans le cadre d'une application de messagerie interne. Le programme permet de gérer des employés, d'envoyer et de recevoir des messages entre eux, tout en assurant la gestion des doublons et en utilisant le modèle de conception DAO (Data Access Object) pour une meilleure organisation du code.

## Fonctionnalités
- Connexion à une base de données MySQL.
- Création des tables `employe` et `message` si elles n'existent pas.
- Gestion des employés :
  - Création, mise à jour, suppression et récupération des enregistrements des employés.
  - Empêche la création d'employés en double basés sur le nom et le prénom.
- Système de messagerie :
  - Envoi de messages entre employés.
  - Empêche la création de messages en double basés sur le contenu et les participants.
  - Récupération de tous les messages ou filtrage par destinataire.
- Gestion des transactions :
  - Utilisation des transactions pour assurer l'intégrité des opérations CRUD.
- Configuration flexible :
  - Configuration des paramètres de connexion via un fichier de propriétés.

## Structure du projet
Le projet contient les classes suivantes :

- **Employe.java** : Classe représentant un employé avec ses attributs et méthodes d'accès.
- **Message.java** : Classe représentant un message avec ses attributs et méthodes d'accès.
- **Connexion.java** : Gère la connexion à la base de données en utilisant JDBC.
- **IDao.java** : Interface générique définissant les opérations CRUD.
- **EmployeService.java** : Implémentation de l'interface `IDao` pour gérer les opérations sur les employés.
- **MessageService.java** : Implémentation de l'interface `IDao` pour gérer les opérations sur les messages.
- **Messagerie.java** : Classe principale contenant la méthode `main` pour exécuter l'application.

## Prérequis
- **Java** : Version 8 ou supérieure.
- **MySQL** : Installé et en cours d'exécution.
- **Pilote JDBC** : `mysql-connector-java.jar` ajouté au classpath du projet.


 **Base de données** :
   - Créez une base de données MySQL nommée `messagerie` :

     ```sql
     CREATE DATABASE messagerie_db;
     ```

   - Créez les tables nécessaires en exécutant les scripts SQL suivants :

     ```sql
     USE messagerie_db;

     -- Table pour Employe
     CREATE TABLE employe (
         id INT AUTO_INCREMENT PRIMARY KEY,
         nom VARCHAR(50) NOT NULL,
         prenom VARCHAR(50) NOT NULL,
         UNIQUE (nom, prenom)
     );

     -- Table pour Message
     CREATE TABLE message (
         id INT AUTO_INCREMENT PRIMARY KEY,
         objet VARCHAR(100) NOT NULL


         sujet VARCHAR(255) NOT NULL,
         date DATE NOT NULL,
         idE INT NOT NULL,
         idR INT NOT NULL,
         FOREIGN KEY (idE) REFERENCES employe(id),
         FOREIGN KEY (idR) REFERENCES employe(id),
         UNIQUE (objet, sujet, idE, idR, date)
     );
     ```

https://github.com/user-attachments/assets/a7e71396-3a43-41a4-b292-e13063cc569f


     
