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
//removed issueID as issue handling is out of scope
public class Period {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Hall hall;
    private PeriodType type;
    private String title;
    private Customer bookedBy;
    private Scheduler scheduledBy;
    private PeriodStatus status;
    
    
    public Period(){
        
    }

    public Period(LocalDateTime startTime, LocalDateTime endTime, Hall hall, PeriodType type, String title, PeriodStatus status, Customer bookedBy, Scheduler scheduledBy) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.hall = hall;
        this.type = type;
        this.title = title;
        this.status = status;
        this.bookedBy = bookedBy;
        this.scheduledBy = scheduledBy;
    }
    
    //modified toString method to include Optional.ofNullable and map
    //to make it more fucntional
    //rather than using mutable vraibles and if statement for validation
    @Override
    public String toString() {
        return startTime + "," +
               endTime + "," +
               hall + "," +
               type + "," +
               title + "," +
               status + "," +
               Optional.ofNullable(bookedBy).map(Customer::getUsername).orElse("null") + "," +
               Optional.ofNullable(scheduledBy).map(Scheduler::getUsername).orElse("null");
    }
    
    
    //modified the parse method form the imperative program
    //passing the likst of users directly instaec of reaidng them inside the method
    //removed some varaibles to enhance immutability
    //use fucntional concepts such as map, filter
    //returning the new Period object similarly to the original method
    public static Period parse(String line, List<User> users) {
        String[] col = line.split(",");

        Customer bookedBy = users.stream()
                .filter(u -> u instanceof Customer)//filtering for customers only
                .map(u -> (Customer) u)//trasnfroming to customer through map
                .filter(c -> c.getUsername().equals(col[8]))//matching the customer to the username of the line 
                .findFirst() // finding the fist match with the username
                .orElse(null); 

        Scheduler scheduledBy = users.stream()
                .filter(u -> u instanceof Scheduler)
                .map(u -> (Scheduler) u)
                .filter(s -> s.getUsername().equals(col[9]))
                .findFirst()
                .orElse(null);

        return new Period(
                LocalDateTime.parse(col[0]),
                LocalDateTime.parse(col[1]),
                Hall.parse(col[2] + "," + col[3] + "," + col[4]),
                PeriodType.valueOf(col[5]),
                col[6],
                PeriodStatus.valueOf(col[7]),
                bookedBy,
                scheduledBy
        );
        //creating and returning the new period object
    }

    
    
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public PeriodType getType() {
        return type;
    }

    public void setType(PeriodType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public Customer getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(Customer bookedBy) {
        this.bookedBy = bookedBy;
    }

    public Scheduler getScheduledBy() {
        return scheduledBy;
    }

    public void setScheduledBy(Scheduler scheduledBy) {
        this.scheduledBy = scheduledBy;
    }

    public PeriodStatus getStatus() {
        return status;
    }

    public void setStatus(PeriodStatus status) {
        this.status = status;
    }

}
