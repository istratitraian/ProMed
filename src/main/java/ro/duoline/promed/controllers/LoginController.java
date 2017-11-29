package ro.duoline.promed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
* @author I.T.W764
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLoginForm() {
        System.out.println("LoginController.showLoginForm()");
        return "login";
    }


    @GetMapping("/access_denied")
    public String notAuth() {
//        return "secured";
        return "access_denied";
    }

    @GetMapping("logout-success")
    public String yourLoggedOut() {

        return "logout-success";
    }

}
