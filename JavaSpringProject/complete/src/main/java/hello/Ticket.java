package hello;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Ticket implements Serializable {

    @Id
    public String id;

    public Seat seat;                       //reserved seat
    public int type;                        //type of the ticket (adult, student, retired)
    public float discount;                  //discount in percentage (float), can be applied directly via multiplying with it
    public int price = 1500;                //standard price of a ticket without any discounts in HUF
    public int price3D = 1700;              //standard price of a 3D ticket without any discounts in HUF

    public Ticket(Seat seat, int type) {
        this.seat = seat;
        this.type = type;
        switch(type){
            case 1:
                discount = 1.0f;
                break;
            case 2:
                discount = 0.5f;
                break;
            case 3:
                discount = 0.75f;
                break;
        }
    }

    public String getId() {
        return id;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(String id) {
        this.id = id;
    }
}

