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
//to diaply halls for the scheduler
//must contain side effects as we are diaplying
public class SchedulerViewHalls {
    public static void display(List<Hall> halls) {
        if (halls.isEmpty()) {
            System.out.println("There are no halls that have been created yet.");
            return;
        }

        System.out.printf("%-20s %-12s %-10s%n",
                "Hall Name", "Hourly Rate", "Seats");
        System.out.println("-----------------------------------------------");

        halls.forEach(h ->
            System.out.printf("%-20s %-12.2f %-10d%n",
                    h.getName(),
                    h.getHourlyRate(),
                    h.getTotalSeats())
        );
    }
}
