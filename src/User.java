/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assignmentaplc;

/**
 *
 * @author user
 */
//abstract class User
//holding the username and password
//is extended by the other roles classes
public abstract class User {
    private String username;
    private String password;

    //parametized constructor
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //abstract method representing the openPanel method to open a Jframe
    //will be defuined by child classes to show a console based menu
    //will not be returning a Jframe

    //will be defuined by child classes to show a console based menu
    public abstract String getMenu(); 
    
    //will be defuined by child classes to handle the input with the Scanner for the menu
    protected abstract void handleMenuInput();

    //method representing the openPanel method to open a Jframe
    //will not be returning a Jframe
    //uses the two methods created above
    public void openPanel() {
        System.out.println(getMenu());
        handleMenuInput();
    }
    
    //toString method to be defined
    public abstract String toString();
    
    
    //method to get the roletype of a user based on on its class instanec
    public RoleType getRole() {
        if (this instanceof Administrator) return RoleType.ADMINISTRATOR;
        if (this instanceof Scheduler) return RoleType.SCHEDULER;
        return RoleType.CUSTOMER;
    }

}
