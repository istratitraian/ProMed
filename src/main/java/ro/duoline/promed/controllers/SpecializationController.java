/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/admin/newspecialization")
    public String newSpecializationGet() {

        return "specialization/spechform";
    }
    @PostMapping("/admin/newspecialization")
    public String newSpecializationPost(String spech) {

        return "specialization/list";
    }

}
