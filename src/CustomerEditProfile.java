/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author user
 */
//class for customer to uopdate his own profile
//does noty update the corresponding booking wuith the updated customer usernsame as the
public class CustomerEditProfile {
    
    //validating input
    public static boolean isValid(String username, String password) {
        return username != null && !username.isBlank()
            && password != null && !password.isBlank();
    }

    //update logic
    //retrung optional customer to preven null chceks and exceptions fucntionally
   public static Optional<Customer> update(Customer currentCustomer, String newUsername, String newPassword) {
       //validating 
       if (currentCustomer == null || !isValid(newUsername, newPassword)) {
            return Optional.empty();
        }

        FileOperation file = FileOperation.getInstance();
        List<User> users = file.readUsers();

        //seraching in the users text file for any matcghing username
        //filtering functional - no loops no mutation
        boolean usernameTaken = users.stream()
            .anyMatch(u -> u.getUsername().equals(newUsername) && !u.getUsername().equals(currentCustomer.getUsername()));

        //retuning empty is username already exists
        if (usernameTaken) {
            return Optional.empty();
        }

        //creating ne wobject and updating in file
        Customer customer = new Customer(newUsername, newPassword);
        file.update(currentCustomer, customer);//side effct 

        return Optional.of(customer);
    }

}
