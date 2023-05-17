/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bankbalona;

/**
 *
 * @author L14Y09W29
 */
public class User {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private int userType = 0;
    
    public User() {
        
    }

    public User(String username, String password, String firstname, String lastname) {
        setUsername(username);
        setPassword(password);
        setFirstname(firstname);
        setLastname(lastname);
    }  

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
