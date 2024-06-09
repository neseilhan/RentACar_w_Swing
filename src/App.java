import core.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {

        Connection con = Db.getInstance();
    }
}
