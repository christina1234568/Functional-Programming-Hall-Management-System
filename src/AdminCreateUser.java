/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author user
 */
//for admin to create users
public class AdminCreateUser {
    
    //takes a string outputs a user
    //creating the user though a lambda for purity
    //use of Function to make createUser a fucntion value
    public static final Function<String[], User> createUser = data -> {
        String username = data[0];
        String password = data[1];
        String role = data[2].toUpperCase();

        //validating the roles based on the enums
        return switch (role) {
            case "SCHEDULER" -> new Scheduler(username, password);
            case "ADMINISTRATOR" -> new Administrator(username, password);
            case "CUSTOMER" -> new Customer(username, password);
            default -> throw new IllegalArgumentException("Invalid role: " + role);
        };
    };

    //mathod to validate user input
    public static boolean validatingInput(String username, String password, String role) {
        return !(username.isBlank() || password.isBlank() || role.isBlank());
    }

    //matching with any user in the text file to check for unique username though stream
    public static boolean usernameExists(List<User> users, String username) {
        return users.stream().anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    //to be used in handldMenuInput to create the user
    public static Optional<User> create(List<User> existingUsers, String username, String password, String role) {
        if (!validatingInput(username, password, role)) return Optional.empty();
        if (usernameExists(existingUsers, username)) return Optional.empty();

        //calling the createUser pure funtion
        try {
            User newUser = createUser.apply(new String[]{username, password, role});
            return Optional.of(newUser);//returning new User or if not empty
        } catch (IllegalArgumentException e) {
            return Optional.empty();//returning empty
        }
    }

    //saving the new user
    public static Optional<User> saveUser(User user) {
        return FileOperation.getInstance().create(user);
    }



}
