package messagerie;

import java.util.Date;

import ma.projet.beans.Employe;
import ma.projet.beans.Message;
import ma.projet.service.EmployeService;
import ma.projet.service.MessageService;

public class Messagerie {

public static void main(String[] args) {
   
    EmployeService es = new EmployeService();
    MessageService ms = new MessageService();

    Employe emp1 = new Employe("LACHGAR", "Mohamed");
    Employe emp2 = new Employe("RAMI", "ALI");
    Employe emp3 = new Employe("SAFI", "Fatima");

 
    if (es.create(emp1)) {
        System.out.println("Employé créé : " + emp1.getId() + " - " + emp1.getNom() + " " + emp1.getPrenom());
    } else {
        Employe existant = es.getByNomPrenom(emp1.getNom(), emp1.getPrenom());
        if (existant != null) {
            emp1 = existant;
            System.out.println("Employé existant récupéré : " + emp1.getId() + " - " + emp1.getNom() + " " + emp1.getPrenom());
        } else {
            System.out.println("Échec de la création de l'employé : " + emp1.getNom() + " " + emp1.getPrenom());
        }
    }


    if (es.create(emp2)) {
        System.out.println("Employé créé : " + emp2.getId() + " - " + emp2.getNom() + " " + emp2.getPrenom());
    } else {
        Employe existant = es.getByNomPrenom(emp2.getNom(), emp2.getPrenom());
        if (existant != null) {
            emp2 = existant;
            System.out.println("Employé existant récupéré : " + emp2.getId() + " - " + emp2.getNom() + " " + emp2.getPrenom());
        } else {
            System.out.println("Échec de la création de l'employé : " + emp2.getNom() + " " + emp2.getPrenom());
        }
    }

    if (es.create(emp3)) {
        System.out.println("Employé créé : " + emp3.getId() + " - " + emp3.getNom() + " " + emp3.getPrenom());
    } else {
        Employe existant = es.getByNomPrenom(emp3.getNom(), emp3.getPrenom());
        if (existant != null) {
            emp3 = existant;
            System.out.println("Employé existant récupéré : " + emp3.getId() + " - " + emp3.getNom() + " " + emp3.getPrenom());
        } else {
            System.out.println("Échec de la création de l'employé : " + emp3.getNom() + " " + emp3.getPrenom());
        }
    }

    Employe e = es.getById(emp3.getId());
    if (e != null) {
        e.setNom("ALAOUI");
        e.setPrenom("Manale");
        if (es.update(e)) {
            System.out.println("Employé mis à jour : " + e.getId() + " - " + e.getNom() + " " + e.getPrenom());
        } else {
            System.out.println("Échec de la mise à jour de l'employé : " + e.getId());
        }
    }

    Employe empToDelete = es.getById(4);
    if (empToDelete != null) {
        if (es.delete(empToDelete)) {
            System.out.println("Employé supprimé : " + empToDelete.getId());
        } else {
            System.out.println("Échec de la suppression de l'employé : " + empToDelete.getId());
        }
    } else {
        System.out.println("Aucun employé trouvé avec l'ID 4 pour suppression.");
    }

    System.out.println("\nListe des employés :");
    for (Employe emp : es.getAll()) {
        System.out.println(emp.getId() + " - " + emp.getNom() + " " + emp.getPrenom());
    }

    System.out.println("\nCréation des messages :");

    Employe sender1 = es.getById(emp1.getId());
    Employe receiver1 = es.getById(emp2.getId());
    Employe receiver2 = es.getById(emp3.getId());

    if (sender1 != null && receiver1 != null) {
        Message msg1 = new Message("Réunion", "Réunion de fin d'année", new Date(), sender1, receiver1);
        if (ms.create(msg1)) {
            System.out.println("Message créé entre " + sender1.getNom() + " et " + receiver1.getNom());
        } else {
            System.out.println("Échec de la création du message entre " + sender1.getNom() + " et " + receiver1.getNom());
        }
    }

   
    if (sender1 != null && receiver2 != null) {
        Message msg2 = new Message("Réunion", "Réunion de fin d'année", new Date(), sender1, receiver2);
        if (ms.create(msg2)) {
            System.out.println("Message créé entre " + sender1.getNom() + " et " + receiver2.getNom());
        } else {
            System.out.println("Échec de la création du message entre " + sender1.getNom() + " et " + receiver2.getNom());
        }
    }

   
    if (emp2 != null && sender1 != null) { 
        Message msg3 = new Message("Stage", "Stage à Marrakech", new Date(), emp2, sender1);
        if (ms.create(msg3)) {
            System.out.println("Message créé entre " + emp2.getNom() + " et " + sender1.getNom());
        } else {
            System.out.println("Échec de la création du message entre " + emp2.getNom() + " et " + sender1.getNom());
        }
    }

    if (emp2 != null && receiver2 != null) {
        Message msg4 = new Message("Stage", "Stage à Marrakech", new Date(), emp2, receiver2);
        if (ms.create(msg4)) {
            System.out.println("Message créé entre " + emp2.getNom() + " et " + receiver2.getNom());
        } else {
            System.out.println("Échec de la création du message entre " + emp2.getNom() + " et " + receiver2.getNom());
        }
    }

    
    if (sender1 != null && receiver1 != null) {
        Message duplicateMsg = new Message("Réunion", "Réunion de fin d'année", new Date(), sender1, receiver1);
        if (ms.create(duplicateMsg)) {
            System.out.println("Message en double créé (ceci ne devrait pas arriver).");
        } else {
            System.out.println("Échec de la création du message en double entre " + sender1.getNom() + " et " + receiver1.getNom());
        }
    }

    
    System.out.println("\nMessages reçus par " + receiver2.getNom() + " :");
    ms.getAll().stream()
            .filter(m -> m.getEmpRecepteur().getId() == receiver2.getId())
            .forEach(m -> System.out.println("Sujet : " + m.getSujet() + ", Objet : " + m.getObjet()));
}
}