/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 *
 * @author user
 */
public class AdminEditUser {
    //pure fucntion to chekc if inputs are blank
    public static boolean isValid(String username, String password) {
        return username != null && !username.isBlank()
            && password != null && !password.isBlank();
    }

    //finding the user by user through filtering
    //returning optional to avoi null exceptions
    public static Optional<User> findByUsername(List<User> users, String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    //using the fucntional interface 
    //creating higher order fucntion
    public static Function<User, User> editUser(String newUsername, String newPassword, RoleType newRole) {
        //returning the ne edited user based on user type
        return oldUser -> switch (newRole) {
            case CUSTOMER -> new Customer(newUsername, newPassword);
            case SCHEDULER -> new Scheduler(newUsername, newPassword);
            case ADMINISTRATOR -> new Administrator(newUsername, newPassword);
        };
    }
    
    //method to eidt the user
    public static Optional<User> edit(List<User> users, String oldUsername, String newUsername, String newPassword, String roleInput) {

        //using the pure fucntion
        if (!isValid(newUsername, newPassword)) {
            return Optional.empty();
        }
        
        //applying the parseRole
        return parseRole(roleInput)
            .flatMap(newRole ->//fallteerns the optionals in one optional
                    findByUsername(users, oldUsername)
                            .map(editUser(newUsername, newPassword, newRole))//applying the function
            );
    }
    
    //to validated the role entered by the user through a pure fucntion
    //returns optional roletype
    public static Optional<RoleType> parseRole(String input) {
        try {
            return Optional.of(RoleType.valueOf(input.trim().toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
