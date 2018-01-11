package ro.duoline.promed.services;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.duoline.promed.domains.User;

@Service("springSecUserDetailsServiceImpl")
public class SpringSecUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Resource(name = "userToUserDetails")
    private Converter<User, UserDetails> userUserDetailsConverter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        return userUserDetailsConverter.convert(user);
    }
}
