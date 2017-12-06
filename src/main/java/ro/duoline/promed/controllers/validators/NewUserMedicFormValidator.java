package ro.duoline.promed.controllers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import ro.duoline.promed.commands.UserMedicForm;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.UserRepository;

/**
 * @author I.T.W764
 */
@Component
public class NewUserMedicFormValidator implements Validator {

    @Autowired
//    @Resource(name="userServiceRepoImpl")
    private UserRepository userRepository;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserMedicForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserMedicForm userForm = (UserMedicForm) target;

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            errors.rejectValue("password", "PasswordsDontMatch.customerForm.password", "Passwords Don't Match");
            errors.rejectValue("passwordConfirm", "PasswordsDontMatch.customerForm.passwordConfirm", "Passwords Don't Match");
            return;
        }

        User existendUser = userRepository.findByEmail(userForm.getEmail());
        if (existendUser != null) {
            errors.rejectValue("email", "error.customer.email.exists");
            return;

        }

        User user = userRepository.findByUsername(userForm.getUserName());
        if (user != null) {
            errors.rejectValue("userName", "error.customer.username.taken");
        }

    }
}
