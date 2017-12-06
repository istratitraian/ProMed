/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.commands;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author I.T.W764
 */
public class UserMedicForm extends UserForm {

    @NotEmpty
    private String specialization;

    private MultipartFile file;

    public UserMedicForm() {
    }

    public UserMedicForm(UserForm userForm) {
        this.setId(userForm.getId());
        this.setVersion(userForm.getVersion());
        this.setEmail(userForm.getEmail());
        this.setFirstName(userForm.getFirstName());
        this.setLastName(userForm.getLastName());
        this.setPhoneNumber(userForm.getPhoneNumber());
        this.setUserName(userForm.getUserName());
        this.setAuthorities(userForm.getAuthorities());
        this.setProfileImage(userForm.getProfileImage());
        

    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

}
