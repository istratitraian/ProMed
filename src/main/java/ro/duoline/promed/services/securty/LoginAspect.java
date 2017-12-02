package ro.duoline.promed.services.securty;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.UserRepository;
import ro.duoline.promed.services.UserDetailsImpl;

/**
 * @author I.T.W764
 */
@Aspect
@Component
public class LoginAspect {

    @Autowired
    private UserRepository userRepository;

    @Pointcut("execution(* org.springframework.security.authentication.AuthenticationProvider.authenticate(..))")
    public void doAuthenticate() {
    }

    @AfterReturning(value = "ro.duoline.promed.services.securty.LoginAspect.doAuthenticate()",
            returning = "authentication")
    public void logAfterAuthenticate(Authentication authentication) {

        if (authentication.isAuthenticated()) {
            User user = userRepository.findByUsername(((UserDetailsImpl) authentication.getPrincipal()).getUsername());
            user.setFailedLoginAttempts(0);
            System.out.println("User AUTHENTICATED  " + user);
            userRepository.save(user);
        }
    }

    @AfterThrowing("ro.duoline.promed.services.securty.LoginAspect.doAuthenticate() && args(authentication)")
    public void logAuthenicationException(Authentication authentication) {

        User user = userRepository.findByUsername((String) authentication.getPrincipal());
        if (user != null) { //user found
            user.addLoginFailAttempt();
            if (user.getFailedLoginAttempts() > 5) {
                user.setEnabled(false);
            }
            System.out.println("Valid User name, updating failed attempts " + user);
            userRepository.save(user);
        }

    }
}
