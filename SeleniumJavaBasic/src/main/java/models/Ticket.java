package models;


class Tickets {
    private String departDate;
    private String departStation;
    private String arriveAt;
    private String seatType;
    private String ticketAmount;

    enum DepartDate {
        DAY_25("25");

        private String value;

        DepartDate(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }


//    public DepartDate departDate = DepartDate.DAY_25;

    public Tickets(String departdate, String departStation, String arriveAt, String seatType, String ticketAmount) {
        this.departDate = departdate;
        this.departStation = departStation;
        this.arriveAt = arriveAt;
        this.seatType = seatType;
        this.ticketAmount = ticketAmount;
    }

    public String getDepartStation() {
        return departStation;
    }

    public String getArriveAt() {
        return arriveAt;
    }

    public String getSeatType() {
        return seatType;
    }

    public String getTicketAmount() {
        return ticketAmount;
    }
}

public class Ticket {
    private String departDate;
    private String departStation;
    private String arriveAt;
    private String seatType;
    private String ticketAmount;

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




