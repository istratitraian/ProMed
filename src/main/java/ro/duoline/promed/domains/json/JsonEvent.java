package ro.duoline.promed.domains.json;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import ro.duoline.promed.domains.DayTimeEvent;

public class JsonEvent implements Serializable {

    public static final long serialVersionUID = 1L;
    private String title;
    private String start;
    private String end;
    private int id;
//    private int clinetId;
//    private int serverId;

    private String phoneNumber;
    private String email;
    private String firstName;
    private String lastName;

    public JsonEvent() {
    }

    public JsonEvent(DayTimeEvent event) {
            Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            this.start = dateFormat.format(event.getStartDate());
            this.end = dateFormat.format(event.getEndDate());
            this.title = event.getDescription();
            this.id = event.getId() == null ? -1 : event.getId();
            this.firstName = "";
            this.lastName = "";
            this.phoneNumber = "";
            this.email = "";

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "JsonEvent{" + id + "]title=" + title + ", start=" + start + ", end=" + end + '}';
    }

}
