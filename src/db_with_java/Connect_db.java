package db_with_java;

import java.sql.*;


public class Connect_db {

    static Connection conn=null;
    public static Connection getConnection() {
        //  Database credentials
        final String USER = "postgres";
        final String PASS = "postgres";
        final String DATABASE = "postgres";

        if (conn != null) return conn;
        // get db, user, pass from settings file
        return getConnection(DATABASE, USER, PASS);
    }


    private static Connection getConnection(String db_name, String user_name, String password) {
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.postgresql.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + db_name, user_name, password);

        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

}
