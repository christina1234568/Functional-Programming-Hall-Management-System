/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;

/**
 *
 * @author user
 */
//fucntional class to serach for a hall
//serach by any name similary to the imperative
//retruns a lits, not a single hall similimarly to the impeartive
public class SchedulerSearchHall {
    
    //pure fucntion to return hall with no side effects
    public static List<Hall> searchByName(List<Hall> halls, String criteria) {
        
        //if no criteria just return and later view all halls
        if (criteria == null || criteria.isEmpty()) {
            return halls;
        }

        //if not then filter after treamin and transform in a list
        return halls.stream()
                .filter(h -> h.getName().contains(criteria))
                .toList();
    }
}
