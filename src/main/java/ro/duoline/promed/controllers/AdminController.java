/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

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
import ro.duoline.promed.commands.UserForm;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.converters.UserToUserForm;
import ro.duoline.promed.domains.User;
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

//    @Autowired
//    private UserToUserForm userToUserForm;
    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("medicForm", new UserForm());
        return "medicform";
    }

    @PostMapping("/admin/newmedic")
    public String saveOrUpdate(@Valid UserForm userForm, BindingResult bindingResult) {

        System.out.println("UserCOntroller.savOrUpdate()" + userForm);

        newUserFormValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("saveOrUpdate(/) hasErrors()" + bindingResult.hasErrors());
            return "admin/medicform";
        }
        User u = userFormToUser.convert(userForm);

        u.addAuthority(SecurityConfig.AUTHORITY_PACIENT);

        User savedUser = userRepository.save(u);
        return "redirect:/user/show/" + savedUser.getId();
    }

}
