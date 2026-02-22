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
//class to allow admo=ins to delte users by seraching
public class AdminDeleteUser {
    
    //functional method to find a user by a username
    //uses Optional
    public static Optional<User> findByUsername(List<User> users, String username) {
        return users.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
                //filteirng and finding returnning the first match of thr username
                
    }
    
    //deleeting the user
    public static void delete(FileOperation file, User user) {
        file.delete(user);
    }

}
