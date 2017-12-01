/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.duoline.promed.commands.SpecializationForm;
import ro.duoline.promed.domains.Specialization;
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
