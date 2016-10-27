package db_with_java;

import java.sql.*;
import java.util.HashMap;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Created by komlancz on 2016.10.24..
 */
public class User {

    private static String hashingSalt;
    private static String hashingPassword;
    private static String saltedHashedPassword;


    // toRegister in Main
    public static HashMap saveUser(String name, String email, String password){
        setHashingSalt();
        setPassword(password);
        HashMap newUser = DatabaseHandler.saveToDb(name, email, saltedHashedPassword);
        return newUser;
    }

    // ************** toLogin in Main -- WITHOUT HASH -- *************
//    public static HashMap getUser(String email, String password){
//        HashMap userDict = DatabaseHandler.getUserFromDb(email, password);
//        // userDict --> {test=11}
//        if (userDict.isEmpty()){
//            Main.toLogin();
//        }
//        return userDict;
//    }

    // create new hashed salt
    private static void setHashingSalt() {
        SecureRandom random = new SecureRandom();
        byte saltInBytes[] = new byte[32];
        random.nextBytes(saltInBytes);
        String salt = Base64.getEncoder().encodeToString(saltInBytes);
        hashingSalt = salt;
    }

    // create new salted hashed password
    private static void setPassword(String userPassword) {
        String readyToBeHashed;
        readyToBeHashed = userPassword + hashingPassword;
        byte[] bytes = readyToBeHashed.getBytes();
        saltedHashedPassword = Base64.getEncoder().encodeToString(bytes);

    }

    // Login in main -- WITH SALTED HASED PASS --
    public static HashMap checkPassword(String email, String userPassword) {
        String hashedInput;
        HashMap userDict = new HashMap();
        String readyToBeHashed = userPassword + hashingPassword;
        byte[] bytes = readyToBeHashed.getBytes();
        hashedInput = Base64.getEncoder().encodeToString(bytes);
        if (hashedInput != null){
            userDict = DatabaseHandler.loginUserFromDb(email, hashedInput);
            return userDict;
        }
        return userDict;
    }

}
