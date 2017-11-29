package ro.duoline.promed.converters;

import javax.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.duoline.promed.commands.UserForm;
import ro.duoline.promed.domains.User;

/**
 * Created by jt on 9/26/16.
 */
@Component
public class UserToUserForm implements Converter<User, UserForm> {


    @Override
    public UserForm convert(User user) {
        UserForm userForm = new UserForm();

        userForm.setId(user.getId());
        userForm.setVersion(user.getVersion());
        userForm.setUserName(user.getUsername());
        userForm.setEmail(user.getEmail());
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setPhoneNumber(user.getPhoneNumber());

        
        return userForm;
    }
}
