package ma.projet.beans;

public class Employe {
    private int id;
    private String nom;
    private String prenom;

    // Constructeur sans ID (pour création)
    public Employe(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    // Constructeur avec ID (pour récupération)
    public Employe(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Getters
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public int getId() {
        return id;
    }

    // Setters
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setId(int id) {
        this.id = id;
    }
}
