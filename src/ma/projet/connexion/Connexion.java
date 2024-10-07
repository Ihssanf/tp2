package ma.projet.connexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Connexion {
    private static Connection connection;

    static {
        try {
            Properties p = new Properties();
            FileInputStream f = new FileInputStream("base.properties");
            p.load(f);
            f.close(); // Fermeture du FileInputStream

            String url = p.getProperty("jdbc.url");
            String login = p.getProperty("jdbc.username");
            String password = p.getProperty("jdbc.password");
            String driver = p.getProperty("jdbc.driver");

            Class.forName(driver);
            connection = DriverManager.getConnection(url, login, password);
            connection.setAutoCommit(true); // Assure que l'auto-commit est activé
            System.out.println("Connexion à la base de données réussie.");
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            System.out.println("Erreur de connexion à la base de données : " + ex.getMessage());
            ex.printStackTrace(); // Pour le débogage
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
