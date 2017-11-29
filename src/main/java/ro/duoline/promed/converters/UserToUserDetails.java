package ro.duoline.promed.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.services.UserDetailsImpl;

/**
 * @author I.T.W764
 */
@Component
public class UserToUserDetails implements Converter<User, UserDetails> {

    @Override
    public UserDetails convert(User user) {
        UserDetailsImpl userDetails = new UserDetailsImpl();
        if (user != null) {
            userDetails.setUsername(user.getUsername());
            userDetails.setPassword(user.getEncryptedPassword());
            userDetails.setEnabled(user.getEnabled());

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

            user.getAuthorities().forEach(role -> {
                authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
            });

            userDetails.setAuthorities(authorities);
        }

        return userDetails;
    }
}
