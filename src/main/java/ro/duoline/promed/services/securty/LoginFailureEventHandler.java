package ro.duoline.promed.services.securty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.UserRepository;

/**
 * @author I.T.W764
 */
@Component
public class LoginFailureEventHandler implements ApplicationListener<LoginFailureEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(LoginFailureEvent event) {

        Authentication authentication = (Authentication) event.getSource();
        System.out.println("LoginEvent Failure for: " + authentication.getPrincipal() + ", " + event.getTimestamp());
        updateUserAccount(authentication);
    }

    public void updateUserAccount(Authentication authentication) {
        User user = userRepository.findByUsername((String) authentication.getPrincipal());
        if (user != null) { //user found
            user.setFailedLoginAttempts(user.getFailedLoginAttempts() + 1);

            if (user.getFailedLoginAttempts() > 5) {
                user.setEnabled(false);
            }
            System.out.println("Valid User name, updating failed attempts " + user);
            userRepository.save(user);
        }
    }
}
