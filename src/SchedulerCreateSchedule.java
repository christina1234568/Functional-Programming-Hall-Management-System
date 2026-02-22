/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 *
 * @author user
 */
//for the scheduler to create booking periods - usually maintenance type
public class SchedulerCreateSchedule {
    private final FileOperation file = FileOperation.getInstance();
    
    //function method to create and save retuning optional period
    //uses fucntional methods such as filter using lambdas
    public Optional<Period> createSchedule(Optional<Period> period){
        return period
                //filerting to chcek if starttime is before end time
                //if true peiod stays if false retruns empty optional
                .filter(p -> p.getStartTime().isBefore(p.getEndTime()))
                .flatMap(p -> file.create(p));
    }
    
    //to parse the date into the correct format
    public Optional<LocalDateTime> parseDateTime(String input) {
        try {
            return Optional.of(LocalDateTime.parse(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //to pasere the type into an  accepted format by retruning opional periodTupe
    public Optional<PeriodType> parsePeriodType(String input) {
        try {
            return Optional.of(PeriodType.valueOf(input.toUpperCase()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
