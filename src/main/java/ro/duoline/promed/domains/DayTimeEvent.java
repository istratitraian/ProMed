/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import ro.duoline.promed.domains.abs.AbstractDomain;
import ro.duoline.promed.enums.EventStatus;

/**
 *
 * @author I.T.W764
 */
@Entity
@Table(name = "day_time_events")
public class DayTimeEvent extends AbstractDomain {

    private EventStatus status;

    public boolean isActive() {
        return status.equals(EventStatus.ACTIVE);
    }

    public boolean isReserved() {
        return status.equals(EventStatus.REZERVED);
    }

//    @Column(name = "DESCRIPTION", length = 65535)
    private String description;


    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne//(optional = true)
//    @JoinTable(name = "User_x_Event",
//            joinColumns = @JoinColumn(name = "EventId"),
//            inverseJoinColumns = @JoinColumn(name = "UserId"))
//    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private User user;
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    @ManyToOne//(optional = true)
//    @JoinTable(name = "User_x_Event",
//            joinColumns = @JoinColumn(name = "EventId"),
//            inverseJoinColumns = @JoinColumn(name = "UserId"))
//    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private User client;
//    private Set<User> users = new HashSet<>();

//    private User clientUser;
    public DayTimeEvent() {
    }

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date endDate;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DayTimeEvent other = (DayTimeEvent) obj;
        return this.hashCode() == other.hashCode();
    }

    @Override
    public String toString() {
        return "DayTimeEvent{" + "status=" + status + ", start=" + startDate + ", end=" + endDate+ '}';
    }

}
