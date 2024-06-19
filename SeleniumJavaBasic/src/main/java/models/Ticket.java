package models;


public class Ticket {
    private String departDate;
    private String departStation;
    private String arriveAt;
    private String seatType;
    private String ticketAmount;

//    public DepartDateEnum DepartDate = DepartDateEnum.DAY_25;


    public Ticket(String departDate, String departStation, String arriveAt, String seatType, String ticketAmount) {
        this.departDate = departDate;
        this.departStation = departStation;
        this.arriveAt = arriveAt;
        this.seatType = seatType;
        this.ticketAmount = ticketAmount;
    }

    public void setDepartDate(String departDate) {
        this.departDate = departDate;
    }

    public String getDepartDate() {
        return departDate;
    }

    public void setDepartStation(String departStation) {
        this.departStation = departStation;
    }

    public String getDepartStation() {
        return departStation;
    }

    public void setArriveAt(String arriveAt) {
        this.arriveAt = arriveAt;
    }

    public String getArriveAt() {
        return arriveAt;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setTicketAmount(String ticketAmount) {
        this.ticketAmount = ticketAmount;
    }

    public String getTicketAmount() {
        return ticketAmount;
    }
}




