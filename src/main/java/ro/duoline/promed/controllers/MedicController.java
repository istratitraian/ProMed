/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import ro.duoline.promed.domains.Picture;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.jpa.PictureRepository;
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

//    private Path path;
//
//    @Autowired
//    private MessageSource messageSource;
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
    public String userInfo(@PathVariable String userName, Model model, Principal principal) {
        User user = userRepository.findByUsername(userName);
        boolean isAuthorized = principal != null
                && principal.getName().equals(user.getUsername())
                && user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC);
        model.addAttribute("isMedicAuthorized", isAuthorized);
        model.addAttribute("medic", user);
        return "medic/show";
    }

    public static final int USERS_PER_PAGE_COUNT = 10;

    @GetMapping("/medic/list/{page}")
//    @GetMapping("/medic/list")
//    public String list(Model model, Pageable pageable) {
    public String list(Model model, @PathVariable(name = "page", required = false) Integer pageIndex) {
//        PageRequest request = new PageRequest(page, 1);
//        PageRequest pageRequest = new PageRequest(page, pageSize, Sort.Direction.ASC);

//        pageIndex = 1;
        List<User> userList = new ArrayList<>(roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers());

        sortUsersById(userList);

        int pageCount;

        if (userList.size() % USERS_PER_PAGE_COUNT != 0) {
            pageCount = userList.size() / USERS_PER_PAGE_COUNT + 1;
        } else {
            pageCount = userList.size() / USERS_PER_PAGE_COUNT;
        }

        int begin = 0;
        if (pageIndex > 1) {
            begin = (pageIndex * USERS_PER_PAGE_COUNT) - USERS_PER_PAGE_COUNT + 1;
        }
        int end = begin + USERS_PER_PAGE_COUNT;

        if (end > userList.size()) {
            end = userList.size();
        }

        model.addAttribute("pageCount", pageCount);
        model.addAttribute("pageIndex", pageIndex);

//        model.addAttribute("userListSize", userList.size());
//        model.addAttribute("pageSize", USERS_PER_PAGE_COUNT);
//        Page<User> users = repository.findAll(new PageRequest(1, 20));
        model.addAttribute("medics", userList.subList(begin, end));
//        model.addAttribute("medics", roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers());
        return "medic/list";
    }

    public static List<User> sortUsersById(List<User> list) {
        Collections.sort(list, (User o1, User o2) -> o1.getId() > o2.getId() ? 1 : -1);
        return list;
    }

    @GetMapping("/medic/show/{id}")
    public String getUser(@PathVariable Integer id, Model model, Principal principal) {
        User user = userRepository.findOne(id);

        System.out.println("MedicController.getUser(" + id + ") user : " + user.getAuthorities()
                + ", isMEdic=" + user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC)
        );

        boolean isAuthorized = principal != null
                && principal.getName().equals(user.getUsername())
                && user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC);

        model.addAttribute("isMedicAuthorized", isAuthorized);
        model.addAttribute("medic", user);
        return "medic/show";
    }

    @GetMapping("/medic/edit/{id}")
//    @PreAuthorize("#id == authentication.name")
    public String editGet(@PathVariable Integer id, Model model, Principal principal) {
        User user = userRepository.findOne(id);
        boolean isAuthorized = principal.getName().equals(user.getUsername());
        if (!isAuthorized) {
            return "redirect:/access_denied";
        }
        UserMedicForm userMedicForm = new UserMedicForm(userToUserForm.convert(user));
        userMedicForm.setSpecialization(user.getUsersSpecializations().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));
        model.addAttribute("userMedicForm", userMedicForm);

        return "medic/medicformEdit";
    }

    @PostMapping("/medic/edit")
    public String editUser(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {
        editUserFormValidator.validate(userMedicForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "medic/medicformEdit";
        }
        return updateMedic(userMedicForm);
    }

    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("userMedicForm", new UserMedicForm());
        return "medic/medicform";
    }

    @PostMapping("/admin/newmedic")
    public String newMedic(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {
        newUserFormValidator.validate(userMedicForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "medic/medicform";
        }
        return saveMedic(userMedicForm);
    }

    @Autowired
    private PictureRepository pictureRepository;

    private String saveMedic(UserMedicForm userMedicForm) {

        String[] spechs = userMedicForm.getSpecialization().split(",");
        User u = userFormToUser.convert(userMedicForm);
        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        User savedUser = userRepository.save(u);
        MultipartFile file = userMedicForm.getFile();
        if (file != null && !file.isEmpty()) {
            Picture picture = new Picture();
            try {
                picture.setUser(u);
                picture.setImage(file.getBytes());
                pictureRepository.save(picture);
                u.setProfileImage(picture);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from uploaded file.");
            }
        }

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();
        Set<Specialization> existentRepoSpechiaSet = new HashSet<>();
        specializationRepository.findAll().forEach(existentRepoSpechiaSet::add);

        for (String spech : spechs) {

            spech = spech.trim();

            Specialization specialization = new Specialization(spech);
            if (!existentRepoSpechiaSet.contains(specialization)) {
                specializationRepository.save(specialization);
            }
            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);
        return "redirect:/medic/show/" + savedUser.getId();
    }

    private String updateMedic(UserMedicForm userMedicForm) {

        String[] spechs = userMedicForm.getSpecialization().split(",");
        User u = userFormToUser.convert(userMedicForm);
        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        MultipartFile file = userMedicForm.getFile();
        User savedUser;
        if (file != null && !file.isEmpty()) {
            savedUser = userRepository.save(u);
            try {
                Picture picture = new Picture();
                picture.setUser(u);
                picture.setImage(file.getBytes());
                pictureRepository.save(picture);
                u.setProfileImage(picture);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from uploaded file.");
            }
        } else {

            u.setProfileImage(pictureRepository.findByUserId(u.getId()));
            savedUser = userRepository.save(u);
        }

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();

        for (String spech : spechs) {
            spech = spech.trim();
            Specialization specialization = specializationRepository.findByName(spech);
            if (specialization == null) {
                specialization = new Specialization(spech);
                specializationRepository.save(specialization);
            }

            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);
        return "redirect:/medic/show/" + savedUser.getId();
    }

//    private String getProperty(String prop) {
//        return messageSource.getMessage(prop, new Object[]{}, LocaleContextHolder.getLocale());
//    }
}
