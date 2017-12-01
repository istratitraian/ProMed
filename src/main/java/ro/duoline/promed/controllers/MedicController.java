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
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.jpa.RoleRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class MedicController {

//    @Autowired
//    private SpecializationRepository specializationRepository;

//    @Autowired
//    private UserRepository userRepository;
    
     @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/medic/list")
    public String list(Model model) {
        model.addAttribute("medics", roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers());
        return "medic/list";
    }

}
