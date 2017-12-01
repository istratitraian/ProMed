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

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @NotEmpty
    private String specialization;

}
