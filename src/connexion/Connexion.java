
package connexion;

import java.sql.Connection;
import java.sql.DriverManager;


public class Connexion {
    public static Connection connection=null;
    final String url = "jdbc:mysql://localhost/um6p_java_db";
    
    public Connection getConnection(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection= DriverManager.getConnection(url, "root", "");
            return connection;
        } catch (Exception ex) {
            System.out.println("connection problem 1");
        }
        return null;
    }
    

}
