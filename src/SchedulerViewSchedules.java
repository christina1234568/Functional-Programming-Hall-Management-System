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
//viewing scehdules form teh scehduler perspective
//dofferent form admin view so need another class
public class SchedulerViewSchedules {
    
    //to diaply the periods
    public static void display(List<Period> periods){
        if (periods.isEmpty()){
            System.out.println("There are no schedules that have been created yet.");
            return;
        }
        
        //organizing the column and lables
        System.out.printf("%-20s %-20s %-15s %-12s %-20s %-12s %-12s%n",
                "Start Time", "End Time", "Hall Name", "Type", "Title", "Booked By", "Scheduled By");
        System.out.println("-----------------------------------------------------------------------------------------------");

        periods.forEach(p ->
            System.out.printf("%-20s %-20s %-15s %-12s %-20s %-12s %-12s%n",
                    p.getStartTime(),
                    p.getEndTime(),
                    p.getHall().getName(),
                    p.getType(),
                    p.getTitle(),
                    p.getBookedBy() != null ? p.getBookedBy().getUsername() : "-",
                    p.getScheduledBy() != null ? p.getScheduledBy().getUsername() : "-"
            )
        );
   
    }
}
