package hello;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.DateOperators;

import javax.validation.constraints.Email;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Reservation implements Serializable {

    @Id
    public String id;

    public String firstname;                                //User's firstname
    public String lastname;                                 //User's lastname
    public String email;                                    //User's email address
    public String phoneNumber;                              //User's phone number
    public String screeningId;                             //Reserved screening's Id
    public String reservedSeats;
    public List<Ticket> tickets;        //List of reserved seats (list of the room's seats can be accessed from the screening class,
    // but the discounts are easier to handle in a separate class)

    public int menuNumber;                                  // !!!Optional!!! Selected food menu number (All items represented as menus eg: 1 - small popcorn, 2 - small soda, 3 - small popcorn menu (small popcorn + small soda))
    public int paymentType;                                 // Bank card, change, small - large paper
    public int cost;                                        // Total cost of the reservation, calculated automatically from seats, screening type, food menu, discounts
    public boolean paid;                                    // Payment status of the reservation


    public Reservation() { }

    public Reservation(String firstname, String lastname, String phoneNumber, String email, int menuNumber, int paymentType) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.menuNumber = menuNumber;
        this.paymentType = paymentType;
        this.paid = false;
        this.cost = 0;
    }

    /*
    public Reservation(String firstname, String lastname, String email, String phoneNumber, Screening screening, int paymentType) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.screening = screening;
        this.menuNumber = 0;
        this.paymentType = paymentType;
        this.paid = false;
    }

    */

    public String getId() { return id; }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public int getMenuNumber() {
        return menuNumber;
    }

    public int getPaymentType() {
        return paymentType;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean isPaid() { return paid; }

    public void setPaid() { this.paid = true; }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType = paymentType;
    }

    public void setMenuNumber(int menuNumber) {
        this.menuNumber = menuNumber;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }

    public String getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(String reservedSeats) {
        this.reservedSeats = reservedSeats;
    }
}
