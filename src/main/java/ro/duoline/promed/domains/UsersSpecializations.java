/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.domains;

import ro.duoline.promed.domains.abs.AbstractDomain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author I.T.W764
 */
@Entity
@Table(name = "Users_x_Specializations")
public class UsersSpecializations extends AbstractDomain {

    private static final long serialVersionUID = 1L;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    @JoinColumn(name = "specialization_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialization specialization;

    public UsersSpecializations() {

    }

    public UsersSpecializations(User user, Specialization specialization) {
        this.user = user;
        this.specialization = specialization;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    @Override
    public int hashCode() {
        return (specialization.hashCode() + ":" + user.hashCode()).hashCode();
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
        return this.hashCode() == ((UsersSpecializations) obj).hashCode();
    }

    @Override
    public String toString() {
        return specialization.getName();
    }

}
