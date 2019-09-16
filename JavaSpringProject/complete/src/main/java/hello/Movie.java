package hello;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class Movie implements Serializable {

    @Id
    public String id;
    public String title;
    public String description;



    public String posterLink;
    public int durationInMinutes;

    public Movie(String title, String description,String posterLink,int durationInMinutes) {
        this.description = description;
        this.title = title;
        this.posterLink = posterLink;
        this.durationInMinutes = durationInMinutes;
    }

    public String getId(){return id;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) {

        this.durationInMinutes = durationInMinutes;
    }

    public String getPosterLink() {
        return posterLink;
    }

    public void setPosterLink(String posterLink) {
        this.posterLink = posterLink;
    }



}
