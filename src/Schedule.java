/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.util.List;

/**
 *
 * @author user
 */
//the scehdule represent the booking periods
//enforces immutability by returining new schedules nstead of modifying the current ones
//this clss is not used in the imperative program
//but it has been translated to show a more fucntiona way of organizing the class 
public class Schedule {
    private final List<Period> periods;

    private Schedule() {
        FileOperation file = FileOperation.getInstance();

        //reading users
        List<User> users = file.readUsers();

        //readings periods
        this.periods = file.readPeriods();
    }

    public static Schedule getInstance() {
        return ScheduleHolder.INSTANCE;
    }

    public List<Period> getPeriods() {
        return List.copyOf(periods);
    }

    private Schedule(List<Period> periods) {
        this.periods = List.copyOf(periods);
    }

    private static class ScheduleHolder {
        private static final Schedule INSTANCE = new Schedule();
    }

}
