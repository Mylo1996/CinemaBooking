package hello;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Seat implements Serializable {

    @Id
    public String id;

    public int number;              //seat's unique number, minimum 1
    public boolean free;          //status of seat reservation, if free then return = true

    public Seat(int number) {
        this.number = number;
        this.free = true;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isFree() {
        return free;
    }

    public void reserve() {         //sets the seat to reserved (isFree = false))sFree = false;
        free = false;
    }

    public void setFree() {
        this.free = true;
    }
}

