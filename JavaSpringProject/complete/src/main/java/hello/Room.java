package hello;

import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {

    @Id
    public String id;

    public int roomNumber;
    public int rowCount;        //number of seat rows
    public int colCount;        //number os seat columns
    public List<Seat> seats = new ArrayList<>();    //list of the seats, don't have to store them in a 2D array
    public String size;         //small, medium or large calculated based on the seat count, not settable

    public Room(int rowCount, int colCount, int roomNumber) {
        this.rowCount = rowCount;
        this.colCount = colCount;
        this.roomNumber = roomNumber;
        for (int i = 0; i < rowCount * colCount; i++) {
            seats.add(new Seat(i + 1));       //populate seats list and give them numbers counting from 1
        }

        //if case for setting size, TBD(toBeDecided)

    }

    public void setSeatByNumber(int seatNumber) {
        seats.get(seatNumber-1).reserve();
    }

    public Seat getSeatByNumber(int seatNumber) {
        return seats.get(seatNumber-1);
    }

    public void setId(String id) { this.id = id; }

    public String getId() { return id; }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public void setColCount(int colCount) {
        this.colCount = colCount;
    }

    public String getSize() {
        return size;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

}

