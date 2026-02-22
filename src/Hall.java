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
public class Hall {
    private String name;
    private double hourlyRate;
    private int totalSeats;

    public Hall(String name, double hourlyRate, int totalSeats) {
        this.name = name;
        this.hourlyRate = hourlyRate;
        this.totalSeats = totalSeats;
    }

    //function method
    //modified parse to include map and Optional.ofNullable 
    public static Hall parse(String line) {
        String[] col = line.split(",");

        return new Hall(
            col[0],
            Optional.ofNullable(col.length > 1 ? col[1] : null)
                    .map(Double::parseDouble)
                    .orElse(0.0),
            Optional.ofNullable(col.length > 2 ? col[2] : null)
                    .map(Integer::parseInt)
                    .orElse(0)
        );
    }
    
    @Override
    public String toString() {
        return name + "," + hourlyRate + "," + totalSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }
}
