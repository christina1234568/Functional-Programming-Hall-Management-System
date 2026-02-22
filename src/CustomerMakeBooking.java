/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author user
 */
public class CustomerMakeBooking {
    
    private final FileOperation file = FileOperation.getInstance();
    
    //create ducntion with side effect - creating the object in the file
    //using filter to validate time and  to aply the create method of fileOperations
    //and retruning optional to mkae it more functional
    public Optional<Period> create(Optional<Period> booking) {
        return booking
                .filter(p -> p.getStartTime().isBefore(p.getEndTime()))
                .flatMap(p -> file.create(p));
    }

    
    //pure fucntional methiod to build the booking object from the customer inputs
    //pssing the input paraams as optional to validate them thorugh faltmap and filter preventing null chceks
    public Optional<Period> makingBooking(Customer customer, Optional<Hall> hall, Optional<String> title,Optional<LocalDate> date, Optional<LocalTime> start, Optional<LocalTime> end) {
        return hall.flatMap(h ->//faltmaps to dela with consecutiove optional parameters
               title.filter(t -> !t.isBlank()).flatMap(t ->
               date.flatMap(d ->
               start.flatMap(s ->
               end.filter(e -> s.isBefore(e))
                   .map(e -> new Period( LocalDateTime.of(d, s), LocalDateTime.of(d, e), h, PeriodType.BOOKING, t, PeriodStatus.ACTIVE, customer, null))))));
                   //building the new period object 
    }


    //pre fucntion to parse the date retuning optional
    public Optional<LocalDate> parseDate(String input) {
        try {
            return Optional.of(LocalDate.parse(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    //pure function to parse the time retuning optional
    public Optional<LocalTime> parseTime(String input) {
        try {
            return Optional.of(LocalTime.parse(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    
    
    //puer function to fing the hall matching the hall name entered by the cutsomer
    public Optional<Hall> findHallByName(String name) {
        return file.readHalls().stream()//stremaing then filtering and finding the first occurennec
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst();
    }
    
}
