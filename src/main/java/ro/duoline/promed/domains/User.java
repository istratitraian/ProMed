package ro.duoline.promed.domains;

import ro.duoline.promed.domains.security.Authority;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  * @author I.T.W764
 */
@Entity
@Table(name = "Users")
public class User extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 1L;

    @Transient
    private String password;
    private String username = "";
    private String encryptedPassword;
    private Boolean enabled = true;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Integer failedLoginAttempts = 0;

    @JoinTable(name = "Users_x_Roles",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

//    @JoinTable(name = "Users_x_Specializations",
//            joinColumns = @JoinColumn(name = "UserId"),
//            inverseJoinColumns = @JoinColumn(name = "SpecializationId"))
//    @ManyToMany(fetch = FetchType.LAZY)
//    private Set<Specialization> specializations = new HashSet<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)//, cascade = CascadeType.REMOVE whill delete Cart AND Customer also !!!!
    private Set<UsersSpecializations> usersSpecializations = new HashSet<>();

    public void addSpecializations(Set<Specialization> spechSet) {
        for (Specialization specialization : spechSet) {
            usersSpecializations.add(new UsersSpecializations(this, specialization));
        }
    }

    public void addSpecialization(Specialization specialization) {
        usersSpecializations.add(new UsersSpecializations(this, specialization));
    }

    public Set<UsersSpecializations> getUsersSpecializations() {
        return usersSpecializations;
    }

    public void setUsersSpecializations(Set<UsersSpecializations> usersSpecializations) {
        this.usersSpecializations = usersSpecializations;
    }

    public User() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> auth) {
        this.authorities = auth;
    }

    public void addAuthority(Authority auth) {
        this.authorities.add(auth);
        auth.getUsers().add(this);
    }

    public void removeAuthority(Authority auth) {
        this.authorities.remove(auth);
        auth.getUsers().remove(this);
    }

//    public Set<Specialization> getSpecializations() {
//        return specializations;
//    }
//
//    public void setSpecializations(Set<Specialization> specializations) {
//        this.specializations = specializations;
//    }
//
//    public void addSpecialization(Specialization specialization) {
//
//        this.specializations.add(specialization);
//        specialization.getUsers().add(this);
//    }
//
//    public void removeSpecialization(Specialization specialization) {
//        this.specializations.remove(specialization);
//        specialization.getUsers().remove(this);
//    }
//       public void addSpecializations(Set<Specialization> set) {
//        this.specializations.addAll(set);
//        set.forEach((specialization) -> {
//            specialization.getUsers().add(this);
//        });
//    }
    public Integer getFailedLoginAttempts() {
        return failedLoginAttempts;
    }

    public void setFailedLoginAttempts(Integer failedLoginAttempts) {
        this.failedLoginAttempts = failedLoginAttempts;
    }

    @Override
    public int hashCode() {
        return username.hashCode();
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
        final User other = (User) obj;
        return Objects.equals(hashCode(), other.hashCode());
    }

    @Override
    public String toString() {
        return "User{" + "failedLoginAttempts=" + failedLoginAttempts + ", username=" + username + ", password=" + password + ", encryptedPassword=" + encryptedPassword + ", enabled=" + enabled + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", phoneNumber=" + phoneNumber + ", authorities=" + authorities + '}';
    }

    public void addLoginFailAttempt() {
        failedLoginAttempts++;
    }

}
