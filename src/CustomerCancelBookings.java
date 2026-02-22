/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.Optional;

/**
 *
 * @author user
 */
public class CustomerCancelBookings {
    private final FileOperation file = FileOperation.getInstance();
    
    //pure fucntion to find the booking with matching title and customer userna,e
    public Optional<Period> findBooking(Customer customer, String title){
        return file.readPeriods().stream()
                .filter(p-> p.getBookedBy() != null)
                .filter(p -> p.getBookedBy().getUsername().equals(customer.getUsername()))
                .filter(p ->p.getTitle().equalsIgnoreCase(title))
                .findFirst();
        //filtering and finindg first occurence
    }
    
    
    //cancelling the booking in the file by updating the period status
    public Optional<Period> cancelBooking(Period booking){
        if (booking.getStatus() == PeriodStatus.CANCELLED){
            return Optional.empty();
        }//if alreday canclled return empty
        
        Period cancelled = new Period(booking.getStartTime(), booking.getEndTime(), booking.getHall(), booking.getType(), booking.getTitle(), PeriodStatus.CANCELLED, booking.getBookedBy(), booking.getScheduledBy());
        
        //updating in file
        file.update(booking, cancelled);//side effect
        return Optional.of(cancelled);
    }
    
    
}
