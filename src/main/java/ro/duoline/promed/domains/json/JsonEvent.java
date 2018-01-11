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
    private boolean allDay;
    private int id;

    public JsonEvent() {
    }

    public JsonEvent(DayTimeEvent event) {

        Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        this.title = event.getDescription();
        this.start = dateFormat.format(event.getStartDate());
        this.end = dateFormat.format(event.getEndDate());
        this.id = event.getId();
    }

    public int getId() {
        return id;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
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
