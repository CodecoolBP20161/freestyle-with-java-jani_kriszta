package db_with_java;

import java.util.*;

/**
 * Created by komlancz on 2016.10.25..
 */
public class UserMenu {

    private static Scanner scan = new Scanner(System.in);
    public static void menu(HashMap user){

        boolean userLoop = true;
        System.out.println("\nHello " + user.keySet().iterator().next() + "!\n");

        while (userLoop) {
//            System.out.println("\nHello " + user.keySet().iterator().next() + "!\n");
            System.out.println("Press 1 to get all information\n" +
                    "Press 2 to search with id\n" +
                    "Press 3 to delete with id\n" +
                    "Press 4 tp create new people\n" +
                    "(Press 0 to return to the main menu)\n");
            int choice = scan.nextInt();

            // get all data
            if (choice == 1) {
                DatabaseHandler.getAll();

                // Search by id
            } else if (choice == 2) {
                System.out.println("\nEnter the id: ");
                int searchId = scan.nextInt();
                DatabaseHandler.searchById(searchId);
            }

            //delete by id
            else if(choice == 3){
                System.out.println("\nEnter the id: ");
                int deleteId = scan.nextInt();
                DatabaseHandler.deleteById(deleteId);
            }

            // create new people
            else if (choice == 4){
                System.out.println("Please enter the name: ");
                String name = scan.next();

                System.out.println("Please enter the e-mail: ");
                String email = scan.next();

                System.out.println("Please enter the phone number: ");
                String phone = scan.next();

                System.out.println("Please enter the address: ");
                String address = scan.next();

                System.out.println("\nSave...\n");
                DatabaseHandler.createNew(name, email, phone, address);
            }
            else if (choice == 8) {
                DatabaseHandler.createPeopleDb();
            } else if (choice == 9) {
                DatabaseHandler.updatePeople();
            } else if (choice == 0) {
                break;
            }
        }
    }
}
