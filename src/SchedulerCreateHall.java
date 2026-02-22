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
//class to be used by scheduler to create hall
public class SchedulerCreateHall {
    
    //fundtional method to return optional hall to  be saved
    //no side effects
    //using optional to prevent null exceptions and checks
    public static Optional<Hall> createHall(String name, String rateString, String seatsString){
        //
        if (name == null || name.trim().isEmpty()){
            return Optional.empty();
        }
        
        try{
            //pasring the data
            double rate = Double.parseDouble(rateString);
            int seats = Integer.parseInt(seatsString);
            
            if (rate <=0 || seats <= 0){
                return Optional.empty();
            }
            
            return Optional.of(new Hall(name.trim(), rate, seats));
        }catch(NumberFormatException e){
            return Optional.empty();
        }
    }
    
    //svaing the hall in the file
    public static void saveHall(Hall hall){
        FileOperation.getInstance().create(hall);
    }
}
