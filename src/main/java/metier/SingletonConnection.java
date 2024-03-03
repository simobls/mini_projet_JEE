package metier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingletonConnection {
    private static Connection cnx ;

//    static {
////        try {
////            Class.forName("com.mysql.cj.jdbc.Driver");
////            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniprojetjee", "root", "");
////            System.out.println("Connection successful");
////        } catch (ClassNotFoundException | SQLException e) {
////            System.err.println("Connection error: " + e.getMessage());
////            
////        }
//    }

    public static Connection getConnection() {
    	try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/miniprojetjee", "root", "");
            System.out.println("Connection successful");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Connection error: " + e.getMessage());
            
        }
        return cnx;
    }
}
