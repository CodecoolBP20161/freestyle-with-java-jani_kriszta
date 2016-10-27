package db_with_java;

import java.sql.*;
import java.util.*;

public class DatabaseHandler {
    private static Connection conn = Connect_db.getConnection();
    private static Statement stmt;
    private static String sql;

    //********************* GET ALL **********************
    public static void getAll() {
        try {
            stmt = conn.createStatement();
            sql = "SELECT * FROM people_data";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");

                System.out.print("ID: " + id);
                System.out.println(" | Data: " +name + " " +email+ " " +phone+ " " +address+"\n");

//                System.out.println(id + " " + name + " "+email+" "+phone+" "+address);
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }
    }

    public static boolean checkEmail(String email, String tale_name){
        try {
            stmt = conn.createStatement();
            sql = "SELECT email FROM "+tale_name+" WHERE email='"+email+"';";
            ResultSet rs = stmt.executeQuery(sql);
            if (!rs.next()){
                return true;
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        return false;
    }

    public static void searchById(int searchId) {
        try {
            stmt = conn.createStatement();
            sql = "SELECT name, email, phone, address FROM people_data WHERE id="+searchId+";";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                System.out.print("Name: " + name);
                System.out.println(" | Data: " +email+" "+phone+" "+address+"\n");
            }
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        }

    }

    public static void deleteById(Integer deleteId){
        try {
            stmt = conn.createStatement();
            sql = "DELETE FROM people_data WHERE id=" + deleteId + ";";
            stmt.executeUpdate(sql);
            System.out.println("Deleted!");
        }catch (SQLException se){
            se.printStackTrace();
        }
    }

    public static void createNew(String name, String email, String phone, String address){
        if (checkEmail(email, "people_data")) {
            try {
                stmt = conn.createStatement();
                sql = "INSERT INTO people_data (name, email, phone, address) VALUES ('" + name + "', '" + email + "', '"
                        + phone + "', '" + address + "');";
                stmt.executeUpdate(sql);
                System.out.println("\nSuccess!");
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        else System.out.println("This email already exists!");
    }

    // ************************ SAVE USER ***********
    public static HashMap saveToDb(String name, String email, String password){
        HashMap<String, String > regUser = new HashMap<>();
        try {
            stmt = conn.createStatement();
            sql = "INSERT INTO user_table (name, email, password) VALUES ('" + name + "', '" + email + "', '" + password + "');";
            stmt.executeUpdate(sql);
            regUser.put(name, email);
//            System.out.println("New user saved.");

        }catch (SQLException se){
            se.printStackTrace();

        }
        return regUser;
    }

    // *********************** LOGIN ********************
    public static HashMap loginUserFromDb(String email, String password){
        HashMap<String, String> loginUser = new HashMap<>();
        try {
            stmt = conn.createStatement();
            sql = "SELECT name, email, password FROM user_table WHERE email='"+email+"';";
             ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                if(!rs.getString("password").equals(password)){
                    System.out.println("Wrong e-mail address or password!");
                    return loginUser;
                }
                loginUser.put(rs.getString("name"), rs.getString("email"));
                System.out.println("Logged in!");
                return loginUser;
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        System.out.println("Wrong e-mail address or password!");
        return loginUser;
    }


    // create test db
    public static void createPeopleDb(){
        try {
            stmt = conn.createStatement();
            sql = "CREATE TABLE IF NOT  EXISTS people_data (id SERIAL, name VARCHAR , email VARCHAR , phone VARCHAR , address VARCHAR );";
            stmt.executeUpdate(sql);


        }catch (SQLException se){
            se.printStackTrace();
        }
    }
//------------------------------------------------- Test DB Table ---------------------------------------------

    // Update the test random table
    public static void updatePeople(){
        String tableName = "people_data";
        ArrayList<String> names = new ArrayList<>(Arrays.asList("Bela", "Erzsi", "Rotab", "Hering", "Vilmos"));
        ArrayList<String> emails = new ArrayList<>(Arrays.asList("jaja@puki@hu", "cica@nyanya.hu", "dagadt@puki.com"));
        ArrayList<String> phones = new ArrayList<>(Arrays.asList("06321551", "564866516", "8488556", "485865"));
        ArrayList<String> addresses = new ArrayList<>(Arrays.asList("Budapest", "Kecskemét", "Bivalybasznád", "Jászkarafasza"));
        for(int i=0; i<30; i++){
            try {
                stmt = conn.createStatement();
                sql = "INSERT INTO people_data (name, email, phone, address) VALUES ('" + getRandomValue(names)
                        + "', '" + getRandomValue(emails) + "', '"
                        +getRandomValue(phones)+"', '"+getRandomValue(addresses)+"' );";
                stmt.executeUpdate(sql);

            }catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    // Get random value form a list --- for random table update
    private static String getRandomValue(ArrayList theList){
        Random random = new Random();
        int item = random.nextInt(theList.size());
        return theList.get(item).toString();
    }

    public static void createUserTable(){
        try {
            stmt = conn.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS User_table\n" +
                    "(\n" +
                    "id SERIAL,\n" +
                    "name varchar(255),\n" +
                    "email varchar(255),\n" +
                    "password varchar(255));";
            stmt.executeUpdate(sql);
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
}
