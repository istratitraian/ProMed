package ro.duoline.promed.domains;

import ro.duoline.promed.domains.security.Authority;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 *  * @author I.T.W764
 */
@Entity
@Table(name = "Users")
public class User extends AbstractDomainClass implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer failedLoginAttempts = 0;

    private String username = "";

    @Transient
    private String password;

    private String encryptedPassword;
    private Boolean enabled = true;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    // @Embedded
    // private Address shippingAddress = new Address();
    @JoinTable(name = "Users_x_Roles",
            joinColumns = @JoinColumn(name = "UserId"),
            inverseJoinColumns = @JoinColumn(name = "RoleId"))
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities = new ArrayList<>();// new HashSet<>();

    public User() {
    }

    public User(String password, String encryptedPassword, String firstName, String lastName, String email, String phoneNumber) {
        this.password = password;
        this.encryptedPassword = encryptedPassword;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> auth) {
        this.authorities = auth;
    }

    public void addAuthority(Authority auth) {

        if (!authorities.contains(auth)) {
            this.authorities.add(auth);
        }
        if (!auth.getUsers().contains(this)) {
            auth.getUsers().add(this);
        }
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

}
