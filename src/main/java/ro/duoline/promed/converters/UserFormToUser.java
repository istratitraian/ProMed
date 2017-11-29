package ro.duoline.promed.converters;

import javax.annotation.Resource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ro.duoline.promed.commands.UserForm;
import ro.duoline.promed.domains.User;

/**
 * Created by jt on 12/22/15.
 */
@Component
public class UserFormToUser implements Converter<UserForm, User> {

    @Resource(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Override
    public User convert(UserForm userForm) {

        User user = new User();

        user.setId(userForm.getId());
        user.setVersion(userForm.getVersion());
        user.setUsername(userForm.getUserName());
        user.setEmail(userForm.getEmail());
        user.setEnabled(true);
        user.setEncryptedPassword(passwordEncoder.encode(userForm.getPassword()));
        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhoneNumber(userForm.getPhoneNumber());

        return user;
    }
}
