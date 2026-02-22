/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author user
 */
public class CustomerViewBookings {
private final Customer customer;
    private final FileOperation file = FileOperation.getInstance();

    ///to assigne the logged in customer to the view
    //top view the bookings only made buythe logged in customer
    public CustomerViewBookings(Customer customer) {
        this.customer = customer;
    }

    //filtering all the bookings made by the logged in customer
    public void viewAll() {
        List<Period> bookings = file.readPeriods().stream()
                .filter(p -> p.getBookedBy() != null)//musst not be null in the file
                .filter(p -> p.getBookedBy().getUsername().equals(customer.getUsername()))//must be quels to customer name
                .collect(Collectors.toList());//collecting into a new list

        //diaplyionig the collected lits
        display(bookings);//side effct
    }

    //for the cutsomer to filter the bookings by date
    //following the imperative logic of filtering by date
    //similar to view all except just filtering the date
    public void viewByDate(LocalDate date) {
        List<Period> bookings = file.readPeriods().stream()
                .filter(p -> p.getBookedBy() != null)
                .filter(p -> p.getBookedBy().getUsername().equals(customer.getUsername()))
                .filter(p -> p.getStartTime().toLocalDate().equals(date))
                .collect(Collectors.toList());

        display(bookings);//side efefct
    }

    //fucntional fucntion to diaply
    //side efefct of printing to console
    private void display(List<Period> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("There are no boookings yet.");
            return;
        }

        //createing the correct format of colyumns and lables
        System.out.printf("%-20s %-20s %-15s %-20s %-20s %-12s%n",
                "Date", "Start Time", "End Time", "Hall", "Title", "Status");

        System.out.println("-------------------------------------------------------------------------------------------------------------------");

        bookings.forEach(p ->
            System.out.printf("%-20s %-20s %-15s %-20s %-20s %-12s%n",
                    p.getStartTime().toLocalDate(),
                    p.getStartTime().toLocalTime(),
                    p.getEndTime().toLocalTime(),
                    p.getHall().getName(),
                    p.getTitle(),
                    p.getStatus()
            )
        );
        System.out.println();
    }
    
    //pure function to parse date
    public Optional<LocalDate> parseDate(String input) {
        try {
            return Optional.of(LocalDate.parse(input));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
