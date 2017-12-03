/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.commands;

import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author I.T.W764
 */
public class UserMedicForm extends UserForm {

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

    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @NotEmpty
    private String specialization;

}
