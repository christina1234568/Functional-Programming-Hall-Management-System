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
//class to allow the scheduler to edit halls
public class SchedulerEditHall {
    
    //pure function to valdiate
    //no side effects
    public static boolean isValid(String name, String rateStr, String seatsStr) {
        //validatiing the input of the user
        try {
            double rate = Double.parseDouble(rateStr);
            int seats = Integer.parseInt(seatsStr);
            return name != null && !name.isBlank() && rate >= 0 && seats > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //pure fucntion
    //does not nodify the current hall
    //juts returns a new hall with the udpate data
    public static Function<Hall, Hall> editHall(String newName, String newRate, String newSeats) {
        return oldHall -> new Hall(
                newName,
                Double.parseDouble(newRate),
                Integer.parseInt(newSeats)
        );
    }

    //method to edit the hall
    //implements the isValid method and edithall method
    //return optional to deal fucntionally with presence or absences
    public static Optional<Hall> editIfValid(Hall oldHall, String newName, String newRate, String newSeats) {
        if (!isValid(newName, newRate, newSeats)) {
            return Optional.empty();
        }
        return Optional.of(editHall(newName, newRate, newSeats).apply(oldHall));
    }

    //pure method to finsd the hall by its name
    //returns optional hall again to prevent null checks and exception
    //cannot use serach as teh search returns a list to align iwth the imperative program
    public static Optional<Hall> findByName(List<Hall> halls, String name) {
        return halls.stream()
                    .filter(h -> h.getName().equalsIgnoreCase(name))
                    .findFirst();
    }
}
