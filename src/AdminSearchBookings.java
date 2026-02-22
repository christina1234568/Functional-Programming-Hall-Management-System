/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author user
 */
//class to serach for booking vy admin
//seraching fro any criteria - date, title, hall
public class AdminSearchBookings {
    //used to transform the date time format
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<Period> search(List<Period> periods, String searchText) {
        String query = searchText.trim().toLowerCase();

        return periods.stream()//transfroming in a stream and filtering fucntionally
            .filter(p -> p.getTitle().toLowerCase().contains(query) ||
                         p.getHall().getName().toLowerCase().contains(query) ||
                         p.getStartTime().format(formatter).toLowerCase().contains(query) ||
                         p.getEndTime().format(formatter).toLowerCase().contains(query) ||
                         p.getType().toString().toLowerCase().contains(query) ||
                         p.getStatus().toString().toLowerCase().contains(query))
            .toList();//transfroming in a new list to be retruned
    }

}
