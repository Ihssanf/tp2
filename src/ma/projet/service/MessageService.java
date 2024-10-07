package ma.projet.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ma.projet.beans.Message;
import ma.projet.connexion.Connexion;
import ma.projet.dao.IDao;

public class MessageService implements IDao<Message> {
    private final EmployeService es;

    public MessageService() {
        es = new EmployeService();
    }

    private boolean isDuplicateMessage(Message message) {
        try {
            String req = "SELECT COUNT(*) FROM message WHERE objet = ? AND sujet = ? AND idE = ? AND idR = ? AND date = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setString(1, message.getObjet());
            ps.setString(2, message.getSujet());
            ps.setInt(3, message.getEmpEmetteur().getId());
            ps.setInt(4, message.getEmpRecepteur().getId());
            ps.setDate(5, new Date(message.getDate().getTime()));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }

    @Override
    public boolean create(Message o) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Connexion.getConnection();
            conn.setAutoCommit(false); // Désactive l'auto-commit

            
            if (isDuplicateMessage(o)) {
                System.out.println("Erreur: Le message existe déjà dans la base de données.");
                conn.rollback();
                return false; 
            }

            String req = "INSERT INTO message (objet, sujet, date, idE, idR) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(req);
            ps.setString(1, o.getObjet());
            ps.setString(2, o.getSujet());
            ps.setDate(3, new Date(o.getDate().getTime()));
            ps.setInt(4, o.getEmpEmetteur().getId());
            ps.setInt(5, o.getEmpRecepteur().getId());
            ps.executeUpdate();
            conn.commit(); 
            return true;
        } catch (SQLException ex) {
            try {
                if (conn != null) {
                    conn.rollback(); 
                }
            } catch (SQLException e) {}
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.setAutoCommit(true); 
            } catch (SQLException ex) {}
        }
        return false;
    }

    @Override
    public boolean update(Message o) {
        try {
            String req = "UPDATE message SET objet = ?, sujet = ?, date = ?, idE = ?, idR = ? WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setString(1, o.getObjet());
            ps.setString(2, o.getSujet());
            ps.setDate(3, new Date(o.getDate().getTime()));
            ps.setInt(4, o.getEmpEmetteur().getId());
            ps.setInt(5, o.getEmpRecepteur().getId());
            ps.setInt(6, o.getId());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                return true; 
            } else {
                System.out.println("Échec de la mise à jour du message dans la base de données.");
                return false; 
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean delete(Message o) {
        try {
            String req = "DELETE FROM message WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, o.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected == 1; 
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public Message getById(int id) {
        Message message = null;
        try {
            String req = "SELECT * FROM message WHERE id = ?";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                message = new Message(
                        rs.getInt("id"),
                        rs.getString("objet"),
                        rs.getString("sujet"),
                        rs.getDate("date"),
                        es.getById(rs.getInt("idE")),
                        es.getById(rs.getInt("idR"))
                );
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return message;
    }

    @Override
    public List<Message> getAll() {
        List<Message> messages = new ArrayList<>();
        try {
            String req = "SELECT * FROM message";
            PreparedStatement ps = Connexion.getConnection().prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                        rs.getInt("id"),
                        rs.getString("objet"),
                        rs.getString("sujet"),
                        rs.getDate("date"),
                        es.getById(rs.getInt("idE")),
                        es.getById(rs.getInt("idR"))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MessageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return messages;
    }
}