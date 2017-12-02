/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import static ro.duoline.promed.PopulateTablesJPA.SPECIALIZATION_CARDIOLOG;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.commands.UserMedicForm;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.jpa.SpecializationRepository;
import ro.duoline.promed.jpa.UserRepository;
import ro.duoline.promed.jpa.UsersSpecializationsRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class AdminController {

    @Resource(name = "newUserFormValidator")
    private Validator newUserFormValidator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormToUser userFormToUser;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private UsersSpecializationsRepository usersSpecializationsRepository;

    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("medicForm", new UserMedicForm());
        return "medic/medicform";
    }

    @PostMapping("/admin/newmedic")
    public String newMedic(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {

        System.out.println("UserCOntroller.newMedic()" + userMedicForm);

        newUserFormValidator.validate(userMedicForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("newMedic(/) hasErrors()" + bindingResult.hasErrors());
            return "medic/medicform";
        }

        String[] spechs = userMedicForm.getSpecialization().split(",");

        User u = userFormToUser.convert(userMedicForm);

        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        User savedUser = userRepository.save(u);

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();

        for (String spech : spechs) {

            Specialization specialization = new Specialization(spech);
            specializationRepository.save(specialization);

            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);

        return "redirect:/medic/show/" + savedUser.getId();
    }

}
