package ma.projet.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ma.projet.beans.Employe;
import ma.projet.connexion.Connexion;
import ma.projet.dao.IDao;

public class EmployeService implements IDao<Employe> {

    
     
    public Employe getByNomPrenom(String nom, String prenom) {
        Employe employe = null;
        try {
            String req = "SELECT * FROM employe WHERE nom = ? AND prenom = ?";
            try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
                ps.setString(1, nom);
                ps.setString(2, prenom);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    employe = new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
                }
                rs.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la récupération de l'employé par nom et prénom", ex);
            ex.printStackTrace();
        }
        return employe;
    }

    @Override
    public boolean create(Employe o) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Connexion.getConnection();
            conn.setAutoCommit(false); // Début de la transaction

            // Vérifier si l'employé existe déjà
            Employe existant = getByNomPrenom(o.getNom(), o.getPrenom());
            if (existant != null) {
                System.out.println("L'employé " + o.getNom() + " " + o.getPrenom() + " existe déjà avec l'ID " + existant.getId());
                conn.rollback(); // Annuler la transaction
                return false;
            }

            String req = "INSERT INTO employe (nom, prenom) VALUES (?, ?)";
            ps = conn.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, o.getNom());
            ps.setString(2, o.getPrenom());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 1) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    o.setId(rs.getInt(1)); // Assigne l'ID généré à l'objet Employe
                }
                conn.commit(); // Valider la transaction
                return true;
            }
            conn.rollback(); // Annuler la transaction en cas de non réussite
        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); 
                }
            } catch (SQLException e) {
            }
            if ("23000".equals(ex.getSQLState())) { 
                System.out.println("Violation de contrainte d'unicité : " + ex.getMessage());
            } else {
                Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la création de l'employé", ex);
            }
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.setAutoCommit(true); 
            } catch (SQLException ex) {
            }
        }
        return false;
    }

    @Override
    public boolean update(Employe o) {
        try {
            // Vérification si le nom existe déjà
            Employe existingEmployee = getByNomPrenom(o.getNom(), o.getPrenom());
            if (existingEmployee != null && existingEmployee.getId() != o.getId()) {
                // Le nom existe déjà, refusez la mise à jour
                System.out.println("Échec de la mise à jour de l'employé: Le nom existe déjà.");
                return false;
            }

            String req = "UPDATE employe SET nom = ?, prenom = ? WHERE id = ?";
            int affectedRows;
            try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
                ps.setString(1, o.getNom());
                ps.setString(2, o.getPrenom());
                ps.setInt(3, o.getId());
                affectedRows = ps.executeUpdate();
            }

            
            if (affectedRows == 1) {
                return true;
            } else {
                
                System.out.println("Échec de la mise à jour de l'employé: Possible violation de la contrainte d'unicité.");
                return false; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la mise à jour de l'employé", ex);
            ex.printStackTrace(); 
        }
        return false;
    }

   

    @Override
    public boolean delete(Employe o) {
        if (o != null) { 
            try {
                String req = "DELETE FROM employe WHERE id = ?";
                int affectedRows;
                try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
                    ps.setInt(1, o.getId());
                    affectedRows = ps.executeUpdate();
                }
                return affectedRows == 1;
            } catch (SQLException ex) {
                Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la suppression de l'employé", ex);
            }
        }
        return false; 
    }

    @Override
    public Employe getById(int id) {
        Employe employe = null;
        try {
            String req = "SELECT * FROM employe WHERE id = ?";
            try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        employe = new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"));
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la récupération de l'employé", ex);
        }
        return employe;
    }

    @Override
    public List<Employe> getAll() {
        List<Employe> employes = new ArrayList<>();
        try {
            String req = "SELECT * FROM employe";
            try (PreparedStatement ps = Connexion.getConnection().prepareStatement(req); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employes.add(new Employe(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom")));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmployeService.class.getName()).log(Level.SEVERE, "Erreur lors de la récupération de tous les employés", ex);
        }
        return employes;
    }
}