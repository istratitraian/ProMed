/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.RoleRepository;
import ro.duoline.promed.jpa.SpecializationRepository;
import ro.duoline.promed.jpa.UsersSpecializationsRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class SearchController {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

//    @Autowired
//    private UsersSpecializationsRepository usersSpecializationsRepository;
    @PostMapping("/search")
    public String search(String searchText, Model model) {
        String text = searchText.toLowerCase();

        List<Specialization> specializationsFounded = new ArrayList<>();

        Iterable<Specialization> allSpechs = specializationRepository.findAll();
        for (Specialization spech : allSpechs) {
            if (spech.getName().toLowerCase().contains(text)) {
                specializationsFounded.add(spech);
            }
        }

//                specializationRepository.findByNameContaining(searchText);
        System.out.println("SearchConteroller.search(" + text + ")");

        Set<User> users = roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers();

        List<User> medicsFounded = new ArrayList<>();

        for (User user : users) {
            if (user.getFirstName().toLowerCase().contains(text) || user.getLastName().toLowerCase().contains(text)) {
                medicsFounded.add(user);
            }
        }

//        List<Specialization> specializationsFounded = new ArrayList<>();
        model.addAttribute("searchedText", searchText);
        model.addAttribute("medicsFounded", medicsFounded);
        model.addAttribute("specializations", specializationsFounded);
        return "search/list";
    }
}
