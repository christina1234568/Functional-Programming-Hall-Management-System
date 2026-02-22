/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 *
 * @author user
 */

//fileoperations class similar to the impeartive
//refactored to be fucntional
//used by admin, scheduler and customer 
public class FileOperation {
    //stored in one map not in may different varaibles
    //compared to imperative method
    private final Map<FileType, Path> files;

    //using map to initilaize all the files
    //mapping the file type to the path
    private FileOperation() {
        System.out.println("Initializing files...");
        files = Map.of(
                FileType.HALLS, initilizeFile("data/halls.txt"),
                FileType.USERS, initilizeFile("data/users.txt"),
                FileType.SCHEDULE, initilizeFile("data/schedule.txt")
        );
    }

    //chceking if file exist
    //if not creating files
    private Path initilizeFile(String path) {
        try {
            Path p = Path.of(path);
            Files.createDirectories(p.getParent());
            if (Files.notExists(p)) {
                Files.createFile(p);
                System.out.println("Created new file: " + p.getFileName());
            } else {
                System.out.println("Using existing file: " + p.getFileName());
            }
            return p;
        } catch (IOException e) {
            throw new RuntimeException("Error initializing file: " + path, e);
        }
    }



    //refactores readFile method
    private List<String> readFile(FileType type) {
        try {//removing while loops to stream, filter and collect
            return Files.lines(files.get(type))
                        .filter(line -> !line.isBlank())
                        .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("There was an erro wncountered in reaidng the file: " + files.get(type).getFileName());
            return List.of();
        }
    }

    //reactored method to write to the file
    //elimiante for loope on arraylist
    //by using bufferedWriter
    private void writeFile(FileType type, String line) {
        try (BufferedWriter bw = Files.newBufferedWriter(files.get(type),StandardOpenOption.CREATE,StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("There was an error encountered in writing to file: " + files.get(type).getFileName());
        }
    }

    //eliminate for loop
    //refactored to use inbuilt Files.write
    private void overwriteFile(FileType type, List<String> lines) {
        try {
            Files.write(files.get(type), lines);
        } catch (IOException e) {
            System.err.println("There was an error encountered in overwriting file: " + files.get(type).getFileName());
        }
    }

    //dleeting the lines matching cinsidtions
    //eliminates for loop and coneditional statements
    private void removeLines(FileType type, java.util.function.Predicate<String> predicate) {
        List<String> updated = readFile(type).stream()
                .filter(predicate.negate())
                .collect(Collectors.toList());
        overwriteFile(type, updated);
        //using steam filter and collect to remove more fucntionally
    }

    //editing the lines inside the files functionally
    //usinf tsrema and map to apply the transform
    //eliminates for loop and if statements
    private void editLines(FileType type, java.util.function.Function<String, String> transform) {
        List<String> updated = readFile(type).stream()
                .map(transform)
                .collect(Collectors.toList());//retruning a new list
        overwriteFile(type, updated);//overwirting the whole file
    }

    //using stream and anymatch to chcek if username taken while creating a new user
    private boolean isUserExists(User u1) {
        return readUsers().stream()
                .anyMatch(u -> u.getUsername().equals(u1.getUsername()));
    }

    //validating the periods before creating a period
    //similar to isUserExists
    private boolean isPeriodClashing(Period p1) {
        List<User> users = readUsers();
        return readPeriods().stream()
                .anyMatch(p2 -> p1.getStartTime().isBefore(p2.getEndTime())
                        && p2.getStartTime().isBefore(p1.getEndTime()));
    }


    //create fucntions to create new objects in the file
    public void create(Hall h) {
        writeFile(FileType.HALLS, h.toString());
    }

    public Optional<User> create(User u) {
        if (isUserExists(u)) {
            System.out.println("The username " + u.getUsername() + " already exists.");
            return Optional.empty();
        }
        writeFile(FileType.USERS, u.toString());
        return Optional.of(u);
    }

    public Optional<Period> create(Period p) {
        if (isPeriodClashing(p)) {
            System.out.println("The booking cannot be created as it clashes with another booking.");
            return Optional.empty();
        }
        writeFile(FileType.SCHEDULE, p.toString());
        return Optional.of(p);
    }


    //reading funtions
    //using map and collect to create list instead of ifs statements and for loops
    //returning the list
    public List<Hall> readHalls() {
        return readFile(FileType.HALLS).stream()
                .map(Hall::parse)
                .collect(Collectors.toList());
    }

    public List<User> readUsers() {
        return readFile(FileType.USERS).stream()
                .map(line -> {
                    String[] col = line.split(",");
                    return switch (RoleType.valueOf(col[2])) {
                        case CUSTOMER -> Customer.parse(line);
                        case SCHEDULER -> Scheduler.parse(line);
                        case ADMINISTRATOR -> Administrator.parse(line);
                    };
                })
                .collect(Collectors.toList());
    }

    public List<Period> readPeriods() {
        List<User> users = readUsers();
        return readFile(FileType.SCHEDULE).stream()
                .map(line -> Period.parse(line, users))
                .collect(Collectors.toList());
    }

    //updtae methods
    //apllying the editLines method
    public void update(Hall oldH, Hall newH) {
        editLines(FileType.HALLS, line -> line.equals(oldH.toString()) ? newH.toString() : line);
    }

    public void update(User oldU, User newU) {
        editLines(FileType.USERS, line -> line.equals(oldU.toString()) ? newU.toString() : line);
    }

    public void update(Period oldP, Period newP) {
        editLines(FileType.SCHEDULE, line -> line.equals(oldP.toString()) ? newP.toString() : line);
    }

    //dleete methods
    //applying the removeLines  fucntion
    public void delete(Hall h) {
        removeLines(FileType.HALLS, line -> line.equals(h.toString()));
    }

    public void delete(User u) {
        removeLines(FileType.USERS, line -> line.equals(u.toString()));
    }

    public void delete(Period p) {
        removeLines(FileType.SCHEDULE, line -> line.equals(p.toString()));
    }

    
    //implementing a singleton to follow the design logic of the impeartive system
    public static FileOperation getInstance() {
        return FileOperationHolder.INSTANCE;
    }

    //final instance created to preserve immutability
    private static class FileOperationHolder {
        private static final FileOperation INSTANCE = new FileOperation();
    }
}
