package ma.projet.beans;

import java.util.Date;

public class Message {
    private int id;
    private String objet;
    private String sujet;
    private Date date;
    private Employe empEmetteur;
    private Employe empRecepteur;

    // Constructeur sans ID (pour création)
    public Message(String objet, String sujet, Date date, Employe empEmetteur, Employe empRecepteur) {
        this.objet = objet;
        this.sujet = sujet;
        this.date = date;
        this.empEmetteur = empEmetteur;
        this.empRecepteur = empRecepteur;
    }

    // Constructeur avec ID (pour récupération)
    public Message(int id, String objet, String sujet, Date date, Employe empEmetteur, Employe empRecepteur) {
        this.id = id;
        this.objet = objet;
        this.sujet = sujet;
        this.date = date;
        this.empEmetteur = empEmetteur;
        this.empRecepteur = empRecepteur;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getObjet() { 
        return objet;
    }

    public String getSujet() {
        return sujet;
    }

    public Date getDate() {
        return date;
    }

    public Employe getEmpEmetteur() {
        return empEmetteur;
    }

    public Employe getEmpRecepteur() {
        return empRecepteur;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setObjet(String objet) { 
        this.objet = objet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setEmpEmetteur(Employe empEmetteur) {
        this.empEmetteur = empEmetteur;
    }

    public void setEmpRecepteur(Employe empRecepteur) {
        this.empRecepteur = empRecepteur;
    }

    // Vous pouvez ajouter d'autres méthodes si nécessaire, par exemple pour formater la date
}