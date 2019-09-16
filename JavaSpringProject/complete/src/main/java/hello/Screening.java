package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.DateOperators;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Screening implements Serializable {

    @Id
    public String id;

    public String movieId;         //Movie title, desctiption, duration
    public String movieTitle;
    public int roomNumber;           //Room number and it's seats, seat numbers
    public Room room;
    public boolean is3D;        //if true then the screening is 3D
    public String startTime;      //Start time of the screening (hh:mm )
    public String endTime;      //End time of the screening (HH:mm )
    public String startDay;      //Start day of the screening (Monday/Tuesday/...)
    public int durationInMinutes;


    public Screening() {    }

    public Screening(String movieId, int roomNumber, Room room, boolean is3D, String startTime, String startDay, int durationInMinutes) {
        this.movieId = movieId;
        this.roomNumber = roomNumber;
        this.room = room;
        this.is3D = is3D;
        this.startTime = startTime;
        this.startDay = startDay;
        this.durationInMinutes = durationInMinutes;
        Date dt = startTimeDate();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, this.durationInMinutes);
        DateFormat df = new SimpleDateFormat("HH:mm");
        this.endTime = df.format(c.getTime());
    }

    public Screening(String movieId, String movieTitle, int roomNumber,Room room, boolean is3D, String startTime, String startDay, int durationInMinutes) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.roomNumber = roomNumber;
        this.room = room;
        this.is3D = is3D;
        this.startTime = startTime;
        this.startDay = startDay;
        this.durationInMinutes = durationInMinutes;
        Date dt = startTimeDate();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, this.durationInMinutes);
        DateFormat df = new SimpleDateFormat("HH:mm");
        this.endTime = df.format(c.getTime());
    }

    private Date startTimeDate(){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date d = null;
        try {
            d = dateFormat.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }

    public String getId() {
        return id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getMovieTitle() { return movieTitle; }

    public void setMovieTitle(String movieTitle) { this.movieTitle = movieTitle; }

    public void setIs3D(boolean is3D) {
        this.is3D = is3D;
    }

    public boolean getIs3D() { return is3D; }

    public String  getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartDay() {
        return startDay;
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getDurationInMinutes() { return durationInMinutes; }

    public void setDurationInMinutes(int durationInMinutes) {

        this.durationInMinutes = durationInMinutes;
        Date dt = startTimeDate();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.MINUTE, this.durationInMinutes);
        DateFormat df = new SimpleDateFormat("HH:mm");
        this.endTime = df.format(c.getTime());
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

