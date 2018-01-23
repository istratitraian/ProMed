/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.converters;

import java.util.ArrayList;
import java.util.List;
import ro.duoline.promed.domains.DayTimeEvent;
import ro.duoline.promed.domains.json.JsonEvent;

/**
 *
 * @author I.T.W764
 */
public class EventsToJson {

    private final List<DayTimeEvent> events;

    private List<JsonEvent> jsonEvents;

    public EventsToJson(List<DayTimeEvent> events) {
        this.events = events;
    }

    public List<JsonEvent> getJsonEvents() {

        jsonEvents = new ArrayList<>(events.size());
        try {
            events.forEach((event) -> {
                jsonEvents.add(new JsonEvent(event));
            });
        } catch (Exception e) {
            System.err.println("EnventToJson.gtJsonEvents() "+e);
        }

        return jsonEvents;
    }

    public List<JsonEvent> getJsonClientEvents() {

        jsonEvents = new ArrayList<>(events.size());
        events.forEach((event) -> {
            JsonEvent jsonEv = new JsonEvent();
//            jsonEv.setTitle(event.getDescription());
//            jsonEv.setStart(event.getStartDate());
//            jsonEv.setTitle(event.getDescription());
            jsonEvents.add(jsonEv);
        });

        return jsonEvents;
    }
}
