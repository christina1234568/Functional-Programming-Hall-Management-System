/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author user
 */
//Customer class refactored to ignored raising issues functionality
public class Customer extends User{
    public Customer(String username, String password){
        super(username, password);
    }

    // functional-style parsing from CSV line
    public static Customer parse(String line){
        String[] col = line.split(",");
        return new Customer(col[0], col[1]);
    }

    //ignored the hall issues as it is out of scope for this multiparadigm version
    @Override
    public String toString(){
        return getUsername() + "," + getPassword() + "," + RoleType.CUSTOMER;
    }


    //pure fucntion to be  passed to openPanel method in User class to diaply the menu
    @Override
    public String getMenu() {
        return "=== Customer Console Panel ===\n" +
               "1. Make Booking\n" +
               "2. View Bookings\n" +
               "3. Filter Bookings\n" +
               "4. Cancel Booking\n" +
               "5. Update Profile\n" +
               "6. Logout";
    }

    // to hanlde the input fo the user from the meny
    @Override
    protected void handleMenuInput() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":{
                    //getting the detaoiol of the new booking
                    System.out.print("Enter hall name: ");
                    String hallName = scanner.nextLine().trim();
                    
                    System.out.print("Enter booking title: ");
                    String title = scanner.nextLine().trim();

                    System.out.print("Enter date (yyyy-MM-dd): ");
                    String dateInput = scanner.nextLine().trim();

                    System.out.print("Enter start time (HH:mm): ");
                    String startInput = scanner.nextLine().trim();

                    System.out.print("Enter end time (HH:mm): ");
                    String endInput = scanner.nextLine().trim();
                    
                    CustomerMakeBooking bookingService = new CustomerMakeBooking();
                    
                    //applying the makingBookingFuntion to return the optional booking
                    //passing the optional parsed date and time with the hleper parse fucntions
                    bookingService.create(bookingService.makingBooking(this, bookingService.findHallByName(hallName),java.util.Optional.of(title), bookingService.parseDate(dateInput), bookingService.parseTime(startInput), bookingService.parseTime(endInput)))
                        .ifPresentOrElse(
                            p -> System.out.println("The booking has succesfully been created."),
                            () -> System.out.println("The inputs you have entered were invalid.")
                        );
                    System.out.println();
                    break;
                }
                case "2":{
                    //viewing the bookings for currect customer
                    new CustomerViewBookings(this).viewAll();
                    System.out.println();
                    break;
                }
                case "3": {
                    //getting the date
                    System.out.print("Enter date to filter (yyyy-MM-dd): ");
                    String dateInput = scanner.nextLine().trim();

                    CustomerViewBookings viewService = new CustomerViewBookings(this);

                    viewService.parseDate(dateInput)
                        .ifPresentOrElse(//dealing with presenec or absence
                            viewService::viewByDate,//applying teh fubcrion
                            () -> System.out.println("the date you entred was invalid. Please enter the correct format.")
                        );

                    System.out.println();
                    break;
                }
                case "4":{
                    System.out.print("Enter booking tite to cancel: ");
                    String title = scanner.nextLine().trim();
                    
                    CustomerCancelBookings cancelService = new CustomerCancelBookings();
                    cancelService
                        .findBooking(this, title)//finding the booking
                        .flatMap(cancelService::cancelBooking)//dealing with consecutive optional 
                            //if booking not mpety passing it to ancelBooking
                        .ifPresentOrElse(//empty or not
                            b -> System.out.println("The booking has been successfully cancelled."),
                            () -> System.out.println("The booking has not beeen found or is already cancelled.")
                        );
                    
                    System.out.println();
                    break;
                }
                case "5": {
                    //getting new details
                    System.out.print("Enter new username: ");
                    String username = scanner.nextLine().trim();

                    System.out.print("Enter new password: ");
                    String password = scanner.nextLine().trim();

                    //applying the fucntion update with teh new details
                    CustomerEditProfile.update(this, username, password)
                        .ifPresentOrElse(
                            updated -> {
                                //cahnging the logged in custiomer detials in this session
                                this.setUsername(updated.getUsername());
                                this.setPassword(updated.getPassword());
                                System.out.println("Your account details have been successfully updated.");
                            },
                            () -> System.out.println("The details you have entred were invalid.")
                        );
                    System.out.println();
                    break;
                }
                case "6":
                    System.out.println("You have been logged out.");
                    running = false;
                    break;
                default:
                    System.out.println("Your choice is invalid. Please enter another number.");
            }
        }
    }

}
