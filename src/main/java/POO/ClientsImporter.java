
package POO;

import java.io.IOException;
import java.sql.*;

public class ClientsImporter {

    public static void main(String[] args) throws IOException, SQLException{

        String jdbcURL = "jdbc:mysql://localhost:3306/telegram";
        String username = "root";
        String password = "Ludov1c1.";


        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected");

        /*
        String sql = "INSERT INTO telegram.vendeurs (id, age) VALUES (6, 35)";
        String sql = "INSERT INTO clients (id, age) VALUES (4, 60)";
        Statement statement = connection.createStatement();
        Statement statement = connection.prepareStatement(sql);
        statement.execute(sql);
        */

        int test = 5;
        int i = 105;

        String sql2 = "INSERT INTO telegram.vendeurs (id, age) VALUES (?, ?)";
        PreparedStatement statement1 = connection.prepareStatement(sql2);
        statement1.setInt(1, i);
        statement1.setInt(2, test);
        statement1.executeUpdate();
        System.out.println(statement1.getMaxRows());

        i++;
        System.out.println("enregistrement fait");
    }
}
