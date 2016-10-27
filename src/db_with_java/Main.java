package db_with_java;

import java.util.HashMap;
import java.util.Scanner;

/**
 * Created by komlancz on 2016.10.24..
 */
public class Main {

    static Scanner scan = new Scanner(System.in);



    public static HashMap toLogin(){
        System.out.println("LOGIN");
        System.out.println("Please enter your e-mail: ");
        String mail = scan.next();

        System.out.println("Please enter your password: ");
        String password = scan.next();

        HashMap loggedUsr = User.checkPassword(mail, password);
        if (!loggedUsr.isEmpty()){
            UserMenu.menu(loggedUsr);
        }
        return loggedUsr;
    }

    public static HashMap toRegister(){
        System.out.println("Please enter your name: ");
        String name = scan.next();

        System.out.println("Please enter your e-mail: ");
        String mail = scan.next();

        System.out.println("Please enter your password: ");
        String password = scan.next();

        System.out.println("Save...\n");
        if (DatabaseHandler.checkEmail(mail, "user_table")){
            HashMap regUsr= User.saveUser(name, mail, password);
            System.out.println(regUsr.keySet().iterator().next()+" is registered!");
            return regUsr;
        }
        System.out.println("\nThis email already exists!\n");
        return new HashMap();
    }

    public static void main(String[] args) {
        boolean mainLoop = true;
        while (mainLoop) {
            System.out.print("What would you like to do: LOGIN or REGISTER? (Press Q to quit.) ");
            String answer = scan.next();
            if (answer.toLowerCase().equals("login")) {
                DatabaseHandler.createUserTable();
                DatabaseHandler.createPeopleDb();
                DatabaseHandler.updatePeople();
;                toLogin();

            } else if (answer.toLowerCase().equals("register")) {
                DatabaseHandler.createUserTable();
                toRegister();

            } else if (answer.toLowerCase().equals("q")) {
                mainLoop = false;

                // WITH THIS YO CAN MAKE USER_TABLE IF THERE IS NOT
            } else if (answer.toLowerCase().equals("admin")){
                DatabaseHandler.createUserTable();
            }
            else{
                System.out.println("Invalid data, try again!");
            }
        }
    }
}
