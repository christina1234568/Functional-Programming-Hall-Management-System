/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author user
 */
//login functionalities will be used in a main general menu
//to let the users access their respective menu 
//will consist of login, exiting the program and registration for customers
public class GeneralMenu {
    
    //using the fileOperation singleton
    private final FileOperation file = FileOperation.getInstance();
    
    //using scanner to get user input as we are using a console based application
    private final Scanner scanner = new Scanner(System.in);
    
    //class the mainMenu
    public void start(){
        mainMenu();
    }
    
    //the genertal menu of the ap
    //the entry point ofr users
    private void mainMenu(){
        System.out.println("\n=== Hall Booking Management System ===");
        System.out.println("1. Login");
        System.out.println("2. Register (Customer)");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
        
        String choice = scanner.nextLine().trim();
        
        //using map to map the user input to the moethod
        //using runnable to store the menu processes
        Map<String, Runnable> menuMapping = Map.of(
            "1", this::login,//excuting login
            "2", this::customerRegistration, //exceutiong customer registration
            "3", () -> System.out.println("The program has been stopped.")//exiting the program
        );
         
        //Optional used to deal with null values if any or  a worng input from user
        Optional.ofNullable(menuMapping.get(choice))
                .ifPresentOrElse(Runnable::run,
                        () -> System.out.println("Please enter a valid choice."));
        
        if (!choice.equals("3")) mainMenu();
        //aplyying tail recursion here to prevent looping but reshowing the menu after invalid input
    }
    
    
    //method to validate the login credentials entered by the user by returning the matching user
    private Optional<User> validatingLogin(String username, String password) {
        return file.readUsers().stream()//redaing the users file 
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password))
                .findFirst();
                //filteirng and finding the first matching item
    }
    
    //for main login
    //used by every user role to acces their respective menu
    public void login(){
        //promting and reading the username and pasword
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        //validating gteh credentials
        validatingLogin(username, password)
            .ifPresentOrElse(//fucntional inbvuilt method that checks if a user is retunred or if null
                this::menu,//calling the menu of the user
                () -> System.out.println("The credentials you entered were invalid.")
            );
    }
    
    //method to create customer user
    private void customerRegistration() {
        //getting details
        System.out.print("Enter new username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        Customer customer = new Customer(username, password);
        file.create(customer)
            .ifPresentOrElse(
                c -> System.out.println("You have been registered successfully. Please login to access the system."),
                () -> System.out.println("Registration failed. Username may already exist or input was invalid.")
            );
    }
    
    //menu method to fetch the menu based on the user
    //calling the handleMenyInput to deal with the inputs of the user
    private void menu(User user) {
        System.out.println("\nWelcome, " + user.getUsername() + "!");
        System.out.println(user.getMenu());
        user.handleMenuInput();
    }
}
