package data;

// MainData.java
import models.Ticket;
import models.User;

import java.util.Map;

public class MainData {
    private Map<String, User> users;
    private Map<String, Ticket> tickets;


    // Getters and Setters
    public Map<String, User> getUsersInfo() {
        return users;
    }

    public void setUsersInfo(Map<String, User> usersInfo) {
        this.users = usersInfo;
    }

    public Map<String, Ticket> getTicketsInfo() {
        return tickets;
    }

    public void setTicketsInfo(Map<String, Ticket> ticketsInfo) {
        this.tickets = ticketsInfo;
    }
}
