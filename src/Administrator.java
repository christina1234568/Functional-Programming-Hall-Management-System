/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author user
 */

//adminitrator class
//holds the menu of the admin
//and the handleMenuInput method of the admin
public class Administrator extends User{
    public Administrator(String username, String password){
        super(username, password);
    }

    //to create new constructor objects
    public static Administrator parse(String line){
        String[] col = line.split(",");
        return new Administrator(col[0], col[1]);
    }

    @Override
    public String toString(){
        return this.getUsername() + "," + this.getPassword() + "," + RoleType.ADMINISTRATOR;
    }
    
    
    //functional method that only retunrs a string
    //the menu of the admin
    @Override
    public String getMenu() {
        return "=== Administrator Console Panel ===\n" +
               "1. Create User\n" +
               "2. View Users\n" +
               "3. Search User\n" +
               "4. Edit User\n" +
               "5. Delete User\n" +
               "6. View Bookings\n" +
               "7. Search Bookings\n" +
               "8. Logout";
    }

    
    //to handle the input of the admin based on the menu
    @Override
    protected void handleMenuInput() {
        Scanner scanner = new Scanner(System.in);
        boolean choosing = true;

        while (choosing) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":{
                    //fetching the users
                    List<User> users = FileOperation.getInstance().readUsers();

                    //getting the credentials input
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine().trim();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine().trim();

                    System.out.print("Enter role (CUSTOMER, SCHEDULER, ADMINISTRATOR): ");
                    String role = scanner.nextLine().trim().toUpperCase();

                    //creating the user through the pure method create
                    AdminCreateUser.create(users, username, password, role)
                        .flatMap(AdminCreateUser::saveUser) // flatMap because saveUser now returns Optional<User>
                        .ifPresentOrElse(
                            newUser -> System.out.println(
                                "The user has been successfully created: " 
                                + newUser.getUsername() + " (" + role + ")"
                            ),
                            () -> System.out.println("Failed to create user. Username may already exist or input invalid.")
                        );

                    System.out.println();
                    break;
                }
                case "2":{
                    //reading users
                    List<User> users = FileOperation.getInstance().readUsers();
                    AdminViewUsers.printUsers(users);//diaplying the users
                    System.out.println();
                    break;
                }
                case "3":{
                    List<User> users = FileOperation.getInstance().readUsers();

                    System.out.print("Enter search text (matches username or role): ");
                    String criteria = scanner.nextLine();

                    //sreaching the user fucntionally
                    List<User> results = AdminSearchUser.search(users, criteria);

                    //displaying the users
                    if (results.isEmpty()) {
                        System.out.println("There are no matching users for your cirteia.\n");
                    } else {
                        AdminViewUsers.printUsers(results);
                    }

                    System.out.println();
                    break;
                }
                case "4": {
                    //reading user
                    List<User> users = FileOperation.getInstance().readUsers();

                    //promting and collecting data
                    System.out.print("Enter username to edit: ");
                    String oldUsername = scanner.nextLine().trim();

                    System.out.print("Enter new username: ");
                    String newUsername = scanner.nextLine().trim();

                    System.out.print("Enter new password: ");
                    String newPassword = scanner.nextLine().trim();

                    System.out.print("Enter new role (CUSTOMER, SCHEDULER, ADMINISTRATOR): ");
                    String role = scanner.nextLine();

                    System.out.print("Confirm update? (yes/no): ");
                    if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                        System.out.println("The update has been cancelled.\n");
                        break;
                    }

                    //applying the edie function
                    AdminEditUser.edit(users, oldUsername, newUsername, newPassword, role)
                        .ifPresentOrElse(//fucntionally checks for presence
                            updatedUser -> {
                                User oldUser = AdminEditUser
                                        .findByUsername(users, oldUsername)
                                        .get();

                                //editing in the file
                                FileOperation.getInstance().update(oldUser, updatedUser);
                                System.out.println("The user has been succesfully updated.\n");
                            },
                            () -> System.out.println("The details entered were invalid.\n")
                    );

                    System.out.println();
                    break;
                }
                case "5": {
                    List<User> users = FileOperation.getInstance().readUsers();

                    //getinng username
                    System.out.print("Enter the username you want to delete: ");
                    String username = scanner.nextLine().trim();

                    AdminDeleteUser.findByUsername(users, username)
                        .ifPresentOrElse(//chceking presence
                            user -> {
                                System.out.print("Please confirm the deletion of the user: " + username + "? (yes/no) ");
                                if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                                    AdminDeleteUser.delete(FileOperation.getInstance(), user);//side effect
                                    System.out.println("The user has successfully been deleted.\n");
                                } else {
                                    System.out.println("The deltion process has been cancelled.\n");
                                }
                            },
                            () -> System.out.println("The username has not been found.\n")
                        );
                    System.out.println();
                    break;
                }
                case "6": {
                    List<Period> bookings = FileOperation.getInstance().readPeriods();
                    System.out.println("=== Viewing all the Bookings ===");
                    AdminViewBookings.view(bookings);
                    System.out.println();
                    break;
                }
                case "7": {
                    System.out.print("Enter search text for bookings: ");
                    String searchCriteria = scanner.nextLine();
                    List<Period> bookings = FileOperation.getInstance().readPeriods();
                    //seraching
                    List<Period> filtered = AdminSearchBookings.search(bookings, searchCriteria);
                    //displaying
                    AdminViewBookings.view(filtered);
                    System.out.println();
                    break;
                }
                case "8":
                    System.out.println("You have been logged out.");
                    choosing = false;
                    break;
                default:
                    System.out.println("Your choice is invalid. Please enter another number.");
                    System.out.println();
            }
        }
        
    }
}
