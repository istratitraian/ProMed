package ro.duoline.promed.controllers;

import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.commands.UserForm;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.converters.UserToUserForm;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.UserRepository;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormToUser userFormToUser;

    @Autowired
    private UserToUserForm userToUserForm;

    @Resource(name = "newUserFormValidator")
    private Validator newUserFormValidator;

    @Resource(name = "editUserFormValidator")
    private Validator editUserFormValidator;

    @GetMapping("/user/list")
    public String list(Model model) {
        System.out.println("UserController.list() " + userRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "user/list";
    }

    @GetMapping("/user/show/{id}")
    public String getUser(@PathVariable Integer id, Model model) {
        model.addAttribute("user", userRepository.findOne(id));
        return "user/show";
    }
    @GetMapping("/user/info/{userName}")
    public String userInfo(@PathVariable String userName, Model model) {
        model.addAttribute("user", userRepository.findByUsername(userName));
        return "user/show";
    }

    @GetMapping("/admin/delete/{id}")
    public String edit(@PathVariable Integer id) {

        userRepository.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/new")
    public String newUser(Model model) {
        model.addAttribute("userForm", new UserForm());
        return "user/userform";
    }

    @PostMapping("/user/")
    public String saveOrUpdate(@Valid UserForm userForm, BindingResult bindingResult) {

        System.out.println("UserCOntroller.savOrUpdate()" + userForm);

        newUserFormValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            System.out.println("saveOrUpdate(/) hasErrors()" + bindingResult.hasErrors());
            return "user/userform";
        }
        User u = userFormToUser.convert(userForm);

        u.addAuthority(SecurityConfig.AUTHORITY_PACIENT);

        User savedUser = userRepository.save(u);
        return "redirect:/user/show/" + savedUser.getId();
    }

    @GetMapping("/admin/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("userForm", userToUserForm.convert(userRepository.findOne(id)));
        return "user/userformEdit";
    }

    @PostMapping("/user/edit")
    public String editUser(@Valid UserForm userForm, BindingResult bindingResult) {

        System.out.println("UserController.editUser()" + userForm);

        editUserFormValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return "user/userformEdit";
        }
        User u = userFormToUser.convert(userForm);

        u.addAuthority(SecurityConfig.AUTHORITY_PACIENT);

        User savedUser = userRepository.save(u);
        return "redirect:/user/show/" + savedUser.getId();
    }

}
