package ro.duoline.promed.domains;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *  * @author I.T.W764
 */
@Entity
@Table(name = "Specializations")
public class Specialization extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 222L;

    @Column(unique = true)
    private String name = "";

//    @JoinTable(name = "Users_x_Specializations",
//            joinColumns = @JoinColumn(name = "SpecializationId"),
//            inverseJoinColumns = @JoinColumn(name = "UserId"))
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY, orphanRemoval = true)//, cascade = CascadeType.REMOVE whill delete Cart AND Customer also !!!!
    private Set<UsersSpecializations> usersSpecializations = new HashSet<>();

    
    public Set<UsersSpecializations> getUsersSpecializations() {
        return usersSpecializations;
    }

    public void setUsersSpecializations(Set<UsersSpecializations> usersSpecializations) {
        this.usersSpecializations = usersSpecializations;
    }

    @OneToMany(mappedBy = "specialization", fetch = FetchType.LAZY, orphanRemoval = true)//cascade = CascadeType.REMOVE whill delete Product also !!!
//    @JsonIgnore
    private Set<Service> services = new HashSet<>();

    public Specialization() {
    }

    public Specialization(String name) {
        this.name = name;
    }

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }
//
//    public void addUser(User user) {
//        this.users.add(user);
//        user.getSpecializations().add(this);
//    }
//
//    public void removeUser(User user) {
//        this.users.remove(user);
//        user.getSpecializations().remove(this);
//    }

    @Override
    public int hashCode() {
        return name.hashCode();
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
        final Specialization other = (Specialization) obj;
        return Objects.equals(hashCode(), other.hashCode());
    }

    @Override
    public String toString() {
        return "Specialization{[ " + getId() + "] name=" + name + ", services=" + services + '}';
    }

}
