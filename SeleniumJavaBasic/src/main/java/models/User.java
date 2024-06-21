package models;

import org.openqa.selenium.WebDriver;

public class User {
    private String email;
    private String password;
    private String invalidPassword;
    private String pid;

    private WebDriver driver;

    public User() {
    }

    public void User(WebDriver driver) {
        this.driver = driver;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User (String email, String password, String pid) {
        this.email = email;
        this.password = password;
        this.pid = pid;
    }
    public User(String email, String password, String invalidPassword, String pid) {
        this.email = email;
        this.password = password;
        this.invalidPassword = invalidPassword;
        this.pid = pid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getInvalidPassword() {
        return invalidPassword;
    }

    public void setInvalidPassword(String confirmPassword) {
        this.invalidPassword = confirmPassword;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
