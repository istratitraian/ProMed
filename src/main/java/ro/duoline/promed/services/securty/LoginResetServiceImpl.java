/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.services.securty;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.services.UserService;

/**
 *
 * @author I.T.W764
 */
@Service
public class LoginResetServiceImpl implements LoginResetService {

    @Autowired
    private UserService userService;

    @Scheduled(fixedRate = 60000)
    @Override
    public void resetFailedLogins() {

        System.out.println("LoginResetServiceImpl.resetFailedLogins()");

        List<User> users = userService.listAll();

        users.forEach(user -> {
            if (!user.getEnabled() || user.getFailedLoginAttempts() > 0) {
                user.setEnabled(true);
                user.setFailedLoginAttempts(0);
                System.out.println("LoginResetServiceImpl reset : " + user);
                userService.saveOrUpdate(user);
            }

        });
    }

}
