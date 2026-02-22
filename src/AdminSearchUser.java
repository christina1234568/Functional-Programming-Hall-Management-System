/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author user
 */
//class to searcg users by admins
public class AdminSearchUser {
    
    //m,ethod to serach by role or username
    public static List<User> search(List<User> users, String searchText) {
        String criteria = searchText.trim().toLowerCase();

        if (criteria.isEmpty()) {
            return users; // returning all the users if user input is empty
        }

        //converting the user list in a stream
        return users.stream()
                .filter(u -> u.getUsername().toLowerCase().contains(criteria) ||
                             u.getRole().toString().toLowerCase().contains(criteria))
                .collect(Collectors.toList());
        //filtering based on role or username though functional opeartion filter
        //filter used a lamda as a prediacted to return boolean
        //collects the matches in a new lits - preserving immutability
        
    }
}
