package co.edu.manuelcardona.brsys;

import java.sql.Time;
import java.util.Date;

public class Turn {
    private int id;
    private String day;
    private String hour;
    private int barber_id;
    private int servide_id;
    private int customer_id;
    private int state;

    public Turn(){

    }

    public Turn(int id, String day, String hour, int barber_id, int servide_id, int customer_id,
                int state) {
        this.id = id;
        this.day = day;
        this.hour = hour;
        this.barber_id = barber_id;
        this.servide_id = servide_id;
        this.customer_id = customer_id;
        this.state = state;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public int getBarber_id() {
        return barber_id;
    }

    public void setBarber_id(int barber_id) {
        this.barber_id = barber_id;
    }

    public int getServide_id() {
        return servide_id;
    }

    public void setServide_id(int servide_id) {
        this.servide_id = servide_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getState() { return state; }

    public void setState(int state) {
        this.state = state;
    }
}
