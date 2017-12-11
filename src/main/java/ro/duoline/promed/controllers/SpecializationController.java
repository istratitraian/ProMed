/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.duoline.promed.commands.SpecializationForm;
import static ro.duoline.promed.controllers.MedicController.sortUsersById;
import ro.duoline.promed.controllers.pagenav.PageNav;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.jpa.SpecializationRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class SpecializationController {

    @Autowired
    private SpecializationRepository specializationRepository;

    @GetMapping("/specialization/list")
    public String list(Model model) {
        model.addAttribute("specializations", specializationRepository.findAll());
        return "specialization/list";
    }

    private final PageNav pageNav = new PageNav(5, "/specialization/medic/list/");
    private List<User> medicList;
    private String specializationName;

    @GetMapping("/specialization/medic/list/{pageIndex}")
    public String list(Model model, @PathVariable Integer pageIndex) {
        model.addAttribute("medics", pageNav.buildPageNav(model, pageIndex, medicList));
        model.addAttribute("specialization", specializationName);

        return "specialization/medics/list";

    }

    @GetMapping("/specialization/medics/{id}")
    public String listMedics(@PathVariable Integer id, Model model) {
        medicList = new ArrayList<>();
        Specialization specialization = specializationRepository.findOne(id);

        List<UsersSpecializations> usersSpecializations = new ArrayList<>(specialization.getUsersSpecializations());
        for (UsersSpecializations usersSpecialization : usersSpecializations) {
            medicList.add(usersSpecialization.getUser());
        }
        sortUsersById(medicList);
        specializationName = specialization.getName();
        return "redirect:/specialization/medic/list/1";
    }

    @GetMapping("/specialization/services/{id}")
    public String listServices(@PathVariable Integer id, Model model) {
        model.addAttribute("services", Arrays.asList("EKG", "Tensiune arteriala", "Examen oftalmologic", "Viziotest"));
        return "specialization/services/list";
    }

    @GetMapping("/admin/newspecialization")
    public String newSpecializationGet(Model model) {
        model.addAttribute("spechForm", new SpecializationForm());
        return "specialization/spechform";
    }

    @PostMapping("/admin/newspecialization")
    public String newSpecializationPost(SpecializationForm spech) {
        System.out.println("SpecializationController.newSpecializationPost(" + spech + ")");
        specializationRepository.save(new Specialization(spech.getName()));
        return "redirect:/specialization/list";
    }

}
