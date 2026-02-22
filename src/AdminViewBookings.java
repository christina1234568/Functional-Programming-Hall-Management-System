/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author user
 */
public class AdminViewBookings {
    //used to diaply date in the correct format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    //rading and fromatting the periods into readable strings
    public static void view(List<Period> periods) {
        if (periods.isEmpty()) {
            System.out.println("No booking has been made yet.");
            return;
        }

        //printing the correct labels and spacing to make tabel like dsiaplys
        System.out.printf("%-20s %-15s %-20s %-20s %-12s %-10s%n",
                "Title", "Hall", "Start Time", "End Time", "Type", "Status");
        System.out.println("-------------------------------------------------------------------------------");

        //putting the correpsonidng attribution into its column
        periods.forEach(p -> System.out.printf("%-20s %-15s %-20s %-20s %-12s %-10s%n",
                p.getTitle(),
                p.getHall().getName(),
                p.getStartTime().format(formatter),
                p.getEndTime().format(formatter),
                p.getType(),
                p.getStatus()));
        //attributes shown corresponds to the attributes shown for the amdin in the imperative program
    }
}
