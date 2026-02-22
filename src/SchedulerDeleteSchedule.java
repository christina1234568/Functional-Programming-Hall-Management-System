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
//class to allow the scheduler to delete a scehdule fucntionally
public class SchedulerDeleteSchedule {
    //pure function to return an optional period with matching title
    public static Optional<Period> findByTitle(List<Period> periods, String title) {
        return periods.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }
    
    //deleting the schedule thorugh FileOperations
    //the seide fefct function
    public static void deleteSchedule(Period period){
        FileOperation.getInstance().delete(period);
    }
}
