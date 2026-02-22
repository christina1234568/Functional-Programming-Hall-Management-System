/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @author user
 */

//class to be uised by admins to view users
public class AdminViewUsers {
    
    //use of functional interface
    //take a user returns a string in the correct format thorugh a lambda
    public static final Function<User, String> formatUser =
            u -> String.format(
                    "%-15s %-15s %-15s",
                    u.getUsername(),
                    u.getPassword(),
                    u.getRole()
            );

    //pure functiona to return a formatted list of strings representing the users
    public static List<String> view(List<User> users) {
        return users.stream()
                .map(formatUser)//using map to apply the function to each user
                .toList();//creates a new list
    }

    //displayingt the label and the formatted users
    //pushes the side effects
    public static void printUsers(List<User> users) {
        System.out.println("USERNAME        PASSWORD        ROLE");
        System.out.println("------------------------------------------");

        view(users).forEach(System.out::println);
    }  
}
