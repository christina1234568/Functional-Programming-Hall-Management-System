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
public class SchedulerDeleteHall {
    //fiding the hall by name
    //pure function
    //just retruns optional hall
    public static Optional<Hall> findByName(List<Hall> halls, String name) {
        return halls.stream()
                    .filter(h -> h.getName().equalsIgnoreCase(name))
                    .findFirst();
    }
    
    //side effects
    //deleting the hall
    public static void deleteHall(Hall hall) {
        FileOperation.getInstance().delete(hall);
    }
}
