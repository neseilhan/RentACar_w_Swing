import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/rentacar",
                    "postgres",
                    "postgres"
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
