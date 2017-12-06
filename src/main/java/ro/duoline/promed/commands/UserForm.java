/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.commands;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import ro.duoline.promed.domains.Picture;
import ro.duoline.promed.domains.security.Authority;

/**
 *
 * @author I.T.W764
 */
public class UserForm {

    private Integer id;
    private Integer version;

    private Set<Authority> authorities = new HashSet<>();

    @NotEmpty
    @Size(min = 2, max = 75)
    private String userName;

    @NotEmpty
    private String password;

    @NotEmpty
    private String passwordConfirm;

    private String firstName;
    private String lastName;

    @NotEmpty
    @Email
    private String email;

    private String phoneNumber;

    private Picture profileImage;

    public Picture getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(Picture profileImage) {
        this.profileImage = profileImage;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordText) {
        this.password = passwordText;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
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
}
