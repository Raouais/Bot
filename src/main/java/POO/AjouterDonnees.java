package POO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AjouterDonnees {

    public Connection setConnection() throws SQLException {
        String jdbcURL = "jdbc:mysql://localhost:3306/telegram";
        String username = "root";
        String password = "Ludov1c1.";


        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected");
        return connection;
    }
    public void setStatement(int indice, double valeur) throws SQLException {
        String sql2 = "INSERT INTO telegram.vendeurs (id, age) VALUES (?, ?)";
        PreparedStatement statement = setConnection().prepareStatement(sql2);
        statement.setInt(1, indice);
        statement.setDouble(2, valeur);
        statement.executeUpdate();

        System.out.println("enregistrement fait");
    }
}
