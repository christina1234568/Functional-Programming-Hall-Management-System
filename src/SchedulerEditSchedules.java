/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author user
 */
//class to allow the scehduler to edit scehdules fucntionally
public class SchedulerEditSchedules {
    
    //pure functional method retruning  an opetional period with matching title
    public static Optional<Period> findByTitle(List<Period> periods, String title) {
        return periods.stream()
                .filter(p -> p.getTitle().equalsIgnoreCase(title))
                .findFirst();
    }

    //fucntional method
    //top validate and return the edited period thpough optional period
    public static Optional<Period> editIfValid(Period oldP, String startStr, String endStr, Hall hall, String typeStr, String newTitle) {
        try {
            LocalDateTime start = LocalDateTime.parse(startStr);
            LocalDateTime end = LocalDateTime.parse(endStr);

            if (!end.isAfter(start)) return Optional.empty();

            //paring the type of the period
            PeriodType type = PeriodType.valueOf(typeStr.toUpperCase());

            //building the new edited object
            //preserves immutability - creates another object and does nbot modify the current object
            //status, bookedBy and scheduledBy is kept the same
            return Optional.of(new Period(start, end, hall, type, newTitle, oldP.getStatus(), oldP.getBookedBy(), oldP.getScheduledBy()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
