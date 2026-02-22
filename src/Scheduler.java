/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *
 * @author user
 */

public class Scheduler extends User{
    private boolean isAssignedHallMaintenance;

    public Scheduler(String username, String password){
        super(username, password);
        this.isAssignedHallMaintenance = false;
    }

    public Scheduler(String username, String password, boolean isAssignedHallMaintenance){
        super(username, password);
        this.isAssignedHallMaintenance = isAssignedHallMaintenance; 
    }

    //parsing method is changed to integrate functional conpets
    //
    public static Scheduler parse(String line){
        String[] col = line.split(",");//breaking the line in columns // no chngae
        
        //return new Scheduler object directly
        //parsing trhe fist and second col directly as username and password respectively
        return new Scheduler(
            col[0],
            col[1],
            Optional.ofNullable(col.length > 2 ? col[2] : null)//using Optional.ofNullable instaed of try-catch
                    .map(Boolean::parseBoolean)
                    .orElse(false)
        );
        //using Optional to chcek if the line has a third column
        //if yes assigne the value of col[2], if not set to null
        //the map function converts the string("true"/"false" to a boolean
        //if the vlause is null - flase is returned.

    }

    //no change
    @Override
    public String toString(){
        return this.getUsername() + "," + this.getPassword() + "," + RoleType.SCHEDULER + "," + this.getIsAssignedHallMaintenance();
    }

    //functional method that only retunrs a string
    @Override
    public String getMenu() {
        return "=== Scheduler Console Panel ===\n" +
               "1. Create Hall\n" +
               "2. View Halls\n" +
               "3. Search Hall\n" +
               "4. Edit Hall\n" + 
               "5. Delete Hall\n" + 
               "6. Create Schedule\n" +
               "7. View Schedules\n" + 
               "8. Edit Schedule\n" + 
               "9. Delete Schedule\n" + 
               "10. Logout";
    }
    
    //method to handle the input of the user and direct accrodingly
    @Override
    protected void handleMenuInput() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.print("Enter choice: ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":{
                    //promting and getting the nescessary details - side effcts
                    System.out.print("Enter hall name: ");
                    String name = scanner.nextLine();
                    
                    System.out.print("Enter hourly rate: ");
                    String rate = scanner.nextLine();
                    
                    System.out.print("Enter total seats: ");
                    String seatsNumber = scanner.nextLine();
                    
                    //applying the fucntional method
                    SchedulerCreateHall.createHall(name, rate, seatsNumber)
                        .ifPresentOrElse(//to deal with absence of the optional hall being returned
                            hall -> {
                                SchedulerCreateHall.saveHall(hall);//saving the created hall
                                System.out.println("The hall you created has been saved.\n");
                            },//else emopty halll; so failed
                            () -> System.out.println("The details that you entered were invalid.\n")
                        );
                    System.out.println();
                    break;
                }
                case "2":{
                    List<Hall> halls = FileOperation.getInstance().readHalls();
                    SchedulerViewHalls.display(halls);
                    System.out.println();
                    break;
                }
                case "3": {
                    List<Hall> halls = FileOperation.getInstance().readHalls();

                    //prompting and collectiong name
                    System.out.print("Enter hall name to search: ");
                    String searchText = scanner.nextLine();

                    //seraching
                    List<Hall> result = SchedulerSearchHall.searchByName(halls, searchText);
                    
                    //using the display form view halls
                    SchedulerViewHalls.display(result);

                    System.out.println();
                    break;
                }
                case "4": {
                    List<Hall> halls = FileOperation.getInstance().readHalls();

                    //collecting the name of the hall to be eidted
                    System.out.print("Enter hall name to edit: ");
                    String oldName = scanner.nextLine().trim();

                    // dinifng he name thorugh the pure fucntion
                    SchedulerEditHall.findByName(halls, oldName).ifPresentOrElse(
                        oldHall -> {
                            //if the optional hall retruned is not empty promt and collect new details
                            System.out.print("Enter new hall name: ");
                            String newName = scanner.nextLine().trim();

                            System.out.print("Enter new hourly rate: ");
                            String newRate = scanner.nextLine().trim();

                            System.out.print("Enter new total seats: ");
                            String newSeats = scanner.nextLine().trim();

                            System.out.print("Confirm update? (yes/no): ");
                            if (!scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                                System.out.println("You hva cancelled the update process.\n");
                                return;
                            }

                            //eidting through the functional fucntion
                            SchedulerEditHall.editIfValid(oldHall, newName, newRate, newSeats)
                                .ifPresentOrElse(//if opetional hall retruned is not empty persist in the file
                                    updatedHall -> {
                                        FileOperation.getInstance().update(oldHall, updatedHall);
                                        System.out.println("The hall has been successfully updated.\n");
                                    },
                                    () -> System.out.println("The details you enetered were invalid.\n")
                                );
                        },
                        () -> System.out.println("The hall has not been found.\n")
                    );

                    System.out.println();
                    break;
                }
                case "5": {
                    List<Hall> halls = FileOperation.getInstance().readHalls();

                    //getting the name of the hall toe be deleted
                    System.out.print("Enter hall name to delete: ");
                    String hallName = scanner.nextLine().trim();

                    System.out.print("Confirm deletion? (yes/no): ");
                    if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                        System.out.println("You have cancelled the deletion process.\n");
                        break;
                    }

                    SchedulerDeleteHall
                        .findByName(halls, hallName)
                        .ifPresentOrElse(//to deal with empty or ot Optional hall retuned
                            hall -> {
                                SchedulerDeleteHall.deleteHall(hall);
                                System.out.println("The hall has been succesfully deleted.\n");
                            },
                            () -> System.out.println("The hall has not been found.\n")
                        );

                    System.out.println();
                    break;
                }
                case "6": { 
                    //promting and getting the detail to create the schedule
                    System.out.print("Enter start time (yyyy-MM-ddTHH:mm): ");
                    String startStr = scanner.nextLine().trim();

                    System.out.print("Enter end time (yyyy-MM-ddTHH:mm): ");
                    String endStr = scanner.nextLine().trim();

                    System.out.print("Enter hall name: ");
                    String hallName = scanner.nextLine().trim();

                    System.out.print("Enter period type (BOOKING / MAINTENANCE): ");
                    String typeStr = scanner.nextLine().trim();

                    System.out.print("Enter title: ");
                    String title = scanner.nextLine().trim();

                    SchedulerCreateSchedule scheduleHelper = new SchedulerCreateSchedule();

                    //seraching for the hall corresponding to the hall name the user enters
                    List<Hall> halls = FileOperation.getInstance().readHalls();
                    Optional<Hall> hallOpt = halls.stream()
                            .filter(h -> h.getName().equalsIgnoreCase(hallName))
                            .findFirst();

                    //creating the new schedule
                    //parsing time and date
                    //using flatmaps to dela with the consecutive optional schedules being returned
                    Optional<Period> created = scheduleHelper.parseDateTime(startStr)
                        .flatMap(start -> scheduleHelper.parseDateTime(endStr)
                            .filter(end -> end.isAfter(start))
                            .flatMap(end -> hallOpt
                                .flatMap(hall -> scheduleHelper.parsePeriodType(typeStr)
                                    .map(type -> new Period(start, end, hall, type, title,
                                            PeriodStatus.ACTIVE, null, this))
                                )
                            )
                        );

                    //applying the createSchedule method and saving the schedule created
                    scheduleHelper.createSchedule(created)
                            .ifPresentOrElse(//to dela with optinal return
                                p -> System.out.println("The schedule has been created: " + p + "\n"),
                                () -> System.out.println("Invalid input, hall not found, or period clashes.\n")
                            );
                    
                    System.out.println();
                    break;
                }
                case "7": {
                    List<Period> schedules = FileOperation.getInstance().readPeriods();
                    SchedulerViewSchedules.display(schedules);
                    System.out.println();
                    break;
                }
                case "8": {
                    List<Period> periods = FileOperation.getInstance().readPeriods();

                    //getting the title of the scehdule to be eidted
                    System.out.print("Enter schedule title to edit: ");
                    String oldTitle = scanner.nextLine().trim();

                    SchedulerEditSchedules.findByTitle(periods, oldTitle).ifPresentOrElse(
                        oldPeriod -> {
                            //if the optionla period retruned is not empty - therefor found
                            
                            //getiing the details to edit the schedule
                            System.out.print("Enter new start time (yyyy-MM-ddTHH:mm): ");
                            String startStr = scanner.nextLine().trim();

                            System.out.print("Enter new end time (yyyy-MM-ddTHH:mm): ");
                            String endStr = scanner.nextLine().trim();

                            System.out.print("Enter new hall name: ");
                            String hallName = scanner.nextLine().trim();

                            System.out.print("Enter new period type (BOOKING / MAINTENANCE): ");
                            String typeStr = scanner.nextLine().trim();

                            System.out.print("Enter new title: ");
                            String newTitle = scanner.nextLine().trim();

                            System.out.print("Confirm update? (yes/no): ");
                            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                                System.out.println("The update process has been cancelled.\n");
                                return;
                            }

                            //finding the hall n=form the hall name entered
                            //filtering and finding the firstmatch
                            //then using flapmap to dela with with consecutive optionals
                            FileOperation.getInstance().readHalls().stream()
                                .filter(h -> h.getName().equalsIgnoreCase(hallName))
                                .findFirst()
                                .flatMap(hall ->
                                    SchedulerEditSchedules.editIfValid(
                                        oldPeriod, startStr, endStr, hall, typeStr, newTitle
                                    )
                                )
                                .ifPresentOrElse(//to deal with presence or absenfe - replaces null checjks with if else
                                    updated -> {
                                        FileOperation.getInstance().update(oldPeriod, updated);
                                        System.out.println("The schedule has successfully been updated.\n");
                                    },
                                    () -> System.out.println("Invalid input or hall not found.\n")
                                );
                        },
                        //if the optional period is empty
                        () -> System.out.println("The schedule for the titel you entered has not been found.\n")
                    );
                    System.out.println();
                    break;
                }
                case "9":{
                    List<Period> periods = FileOperation.getInstance().readPeriods();
                    
                    //getting the title
                    System.out.print("Enter schedule title to delete: ");
                    String title = scanner.nextLine().trim();
                    
                    
                    System.out.print("Confirm deletion? (yes/no): ");
                    if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                        System.out.println("The deletion process has been cancvelled.\n");
                        break;
                    }
                    
                    //applying the fin title and delete methods
                    SchedulerDeleteSchedule.findByTitle(periods, title)
                        .ifPresentOrElse(//checking presence
                            p -> {
                                //if optional returned is not empty - then delete the scehduel
                                SchedulerDeleteSchedule.deleteSchedule(p);
                                System.out.println("The schedule has been successfully deletd.\n");
                            },
                            () -> System.out.println("The scehdule with the title entered has not been found.\n")
                        );
                     
                    System.out.println();
                    break;
                }
                case "10":
                    System.out.println("You have been logged out.");
                    running = false;
                    break;
                default:
                    System.out.println("Your choice is invalid. Please enter another number.");
            }
        }
    }


    public boolean getIsAssignedHallMaintenance() {
        return isAssignedHallMaintenance;
    }

    public void setAssignedHallMaintenance(boolean isAssignedHallMaintenance) {
        this.isAssignedHallMaintenance = isAssignedHallMaintenance;
    }
}
