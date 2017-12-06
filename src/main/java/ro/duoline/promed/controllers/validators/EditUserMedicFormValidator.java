package ro.duoline.promed.controllers.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ro.duoline.promed.commands.UserMedicForm;

/**
 * @author I.T.W764
 */
@Component
public class EditUserMedicFormValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserMedicForm.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {

        UserMedicForm userForm = (UserMedicForm) target;

        System.out.println("EditUserFormValidator.validate() pass=" + userForm.getPassword() + " == " + userForm.getPasswordConfirm());

        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())) {
            errors.rejectValue("password", "PasswordsDontMatch.customerForm.password", "Passwords Don't Match");
            errors.rejectValue("passwordConfirm", "PasswordsDontMatch.customerForm.passwordConfirm", "Passwords Don't Match");
        }
    }
}
