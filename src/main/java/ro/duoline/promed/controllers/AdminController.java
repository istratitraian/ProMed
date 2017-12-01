/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.commands.UserMedicForm;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.SpecializationRepository;
import ro.duoline.promed.jpa.UserRepository;

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

    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("medicForm", new UserMedicForm());
        return "medic/medicform";
    }

    @PostMapping("/admin/newmedic")
    public String saveOrUpdate(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {

        System.out.println("UserCOntroller.savOrUpdate()" + userMedicForm);

        newUserFormValidator.validate(userMedicForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("saveOrUpdate(/) hasErrors()" + bindingResult.hasErrors());
            return "medic/medicform";
        }

        Specialization specialization = specializationRepository.findByName(userMedicForm.getSpecialization());

        String[] spechs = userMedicForm.getSpecialization().split(",");
        List<Specialization> list = new ArrayList<>();
        for (String spech : spechs) {
            list.add(new Specialization(spech));
        }

        if (specialization == null) {

            specializationRepository.save(list);
        }

        User u = userFormToUser.convert(userMedicForm);

        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        u.addSpecialization(specialization);

        User savedUser = userRepository.save(u);
        return "redirect:/medic/show/" + savedUser.getId();
    }

}
