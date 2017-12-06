package ro.duoline.promed.domains;

import java.io.UnsupportedEncodingException;
import ro.duoline.promed.domains.abs.AbstractDomainDateCreated;
import ro.duoline.promed.domains.security.Authority;
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
import org.apache.tomcat.util.codec.binary.Base64;

/**
 *  * @author I.T.W764
 */
@Entity
@Table(name = "Users")
public class User extends AbstractDomainDateCreated {

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

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, orphanRemoval = true)//, cascade = CascadeType.REMOVE whill delete Cart AND Customer also !!!!
    private Set<Picture> pictures = new HashSet<>();

    public Set<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(Set<Picture> pictures) {
        this.pictures = pictures;
    }

    @Transient
    private Picture imageBase64;

//    @Transient
    public String getImageBase64() {
        try {
            if (imageBase64 == null) {
                imageBase64 = pictures.iterator().next();
            }

            return new String(Base64.encodeBase64(imageBase64.getImage()), "UTF-8");

        } catch (UnsupportedEncodingException ex) {
        }
        return "";
    }

    public void addPicture(Picture picture) {
        pictures.add(picture);
        picture.setUser(this);
    }

    public void removePicture(Picture picture) {
        pictures.add(picture);
    }

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
