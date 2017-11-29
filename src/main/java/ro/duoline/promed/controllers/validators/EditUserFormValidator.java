package ro.duoline.promed.controllers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import ro.duoline.promed.commands.UserForm;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.UserRepository;

/**
 * @author I.T.W764
 */
@Component
public class EditUserFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserForm userForm = (UserForm) target;

        System.out.println("EditUserFormValidator.validate() pass=" + userForm.getPassword() + " == " + userForm.getPasswordConfirm());

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            errors.rejectValue("password", "PasswordsDontMatch.customerForm.password", "Passwords Don't Match");
            errors.rejectValue("passwordConfirm", "PasswordsDontMatch.customerForm.passwordConfirm", "Passwords Don't Match");
        }
    }
}
