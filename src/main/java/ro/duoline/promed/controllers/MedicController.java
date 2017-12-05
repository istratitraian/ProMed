/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.commands.UserMedicForm;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.converters.UserToUserForm;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.jpa.RoleRepository;
import ro.duoline.promed.jpa.SpecializationRepository;
import ro.duoline.promed.jpa.UserRepository;
import ro.duoline.promed.jpa.UsersSpecializationsRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class MedicController {

    private Path path;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private RoleRepository roleRepository;

    @Resource(name = "editUserFormValidator")
    private Validator editUserFormValidator;

    @Resource(name = "newUserFormValidator")
    private Validator newUserFormValidator;

    @Autowired
    private UserToUserForm userToUserForm;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormToUser userFormToUser;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private UsersSpecializationsRepository usersSpecializationsRepository;

    @GetMapping("/medic/info/{userName}")
    public String userInfo(@PathVariable String userName, Model model) {
        model.addAttribute("medic", userRepository.findByUsername(userName));
        return "medic/show";
    }

    @GetMapping("/medic/list")
    public String list(Model model) {
        model.addAttribute("medics", roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers());
        return "medic/list";
    }

    @GetMapping("/medic/show/{id}")
    public String getUser(@PathVariable Integer id, Model model) {
        model.addAttribute("medic", userRepository.findOne(id));
        return "medic/show";
    }

    @GetMapping("/medic/edit/{id}")
    public String editGet(@PathVariable Integer id, Model model) {
        User user = userRepository.findOne(id);
        UserMedicForm userMedicForm = new UserMedicForm(userToUserForm.convert(user));

        userMedicForm.setSpecialization(user.getUsersSpecializations().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));

        model.addAttribute("userMedicForm", userMedicForm);
        return "medic/medicformEdit";
    }

    @PostMapping("/medic/edit")
    public String editUser(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {

        System.out.println("MedicController.editUser()" + userMedicForm);

        editUserFormValidator.validate(userMedicForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "medic/medicformEdit";
        }
        return saveMedic(userMedicForm);
    }

    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("medicForm", new UserMedicForm());
        return "medic/medicform";
    }

    @PostMapping("/admin/newmedic")
    public String newMedic(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {

        System.out.println("MedicController.newMedic()" + userMedicForm);

        newUserFormValidator.validate(userMedicForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "medic/medicform";
        }

        return saveMedic(userMedicForm);
    }

    private String saveMedic(UserMedicForm userMedicForm) {

        String[] spechs = userMedicForm.getSpecialization().split(",");

        User u = userFormToUser.convert(userMedicForm);

        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        User savedUser = userRepository.save(u);

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();

        for (String spech : spechs) {

            Specialization specialization = specializationRepository.findByName(spech);

            if (specialization == null) {
                specialization = new Specialization(spech);
                specializationRepository.save(specialization);
            }

            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);

        String rootDir = getProperty("external.path");
        Integer savedId = savedUser.getId();

        path = Paths.get(rootDir + savedId);

        MultipartFile image = userMedicForm.getFile();

        if (image != null && !image.isEmpty()) {
            try {
                image.transferTo(path.toFile());
            } catch (IOException | IllegalStateException e) {
                throw new RuntimeException("Medic image saving failed :(" + image.getName()+" )");
            }
        }

        return "redirect:/medic/show/" + savedUser.getId();
    }

    private String getProperty(String prop) {
        return messageSource.getMessage(prop, new Object[]{}, LocaleContextHolder.getLocale());
    }

}
