package fr.app.theft.entities;

import java.time.LocalDate;

public class Notification {

    private int id;
    private String tag;
    private String message;
    private LocalDate date;


    public Notification(int id, String tag, String message, LocalDate date) {
        this.id = id;
        this.tag = tag;
        this.message = message;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}
