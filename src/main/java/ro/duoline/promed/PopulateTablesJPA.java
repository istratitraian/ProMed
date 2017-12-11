package ro.duoline.promed;

import java.util.ArrayList;
import java.util.List;
import ro.duoline.promed.jpa.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.jpa.RoleRepository;
import ro.duoline.promed.jpa.UserRepository;
import ro.duoline.promed.jpa.UsersSpecializationsRepository;

/**
 * @author I.T.W764
 */
@Component
public class PopulateTablesJPA implements ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Autowired
    private UsersSpecializationsRepository usersSpecializationsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Override
//    @PostConstruct
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadRoles();
        loadSpecializations();
        new Thread(this::loadUsersAndUsers).start();
    }

    private void loadRoles() {
        roleRepository.save(SecurityConfig.AUTHORITY_SU_ADMIN);
        roleRepository.save(SecurityConfig.AUTHORITY_MEDIC);
        roleRepository.save(SecurityConfig.AUTHORITY_PACIENT);
    }

    private void loadSpecializations() {
        specializationRepository.save(SPECIALIZATION_CARDIOLOG);
        specializationRepository.save(SPECIALIZATION_RADIOLOG);
        specializationRepository.save(SPECIALIZATION_STOMATOLOG);
    }

    public void loadUsersAndUsers() {

        List<User> userList = new ArrayList<>();
        List<UsersSpecializations> usersSpechsList = new ArrayList<>();

        User user0 = new User();
        user0.addAuthority(SecurityConfig.AUTHORITY_SU_ADMIN);
        user0.setUsername("admin");
        user0.setEncryptedPassword(passwordEncoder.encode("admin"));
        user0.setFirstName("Istrati");
        user0.setLastName("Taian");
        user0.setEmail("istrati.traian@yahoo.com");
        user0.setPhoneNumber("+40748661049");

        userRepository.save(user0);

        for (int i = 0; i < 10; i++) {
            User user1 = new User();
            user1.addAuthority(SecurityConfig.AUTHORITY_PACIENT);
            user1.setUsername("pacient_" + i);
            user1.setEncryptedPassword(passwordEncoder.encode("pass"));
            user1.setFirstName("Name" + i);
            user1.setLastName("LastName" + i);
            user1.setEmail(i + "pacient@test.com");
            user1.setPhoneNumber("074800000" + i);
//            userRepository.save(user1);
            userList.add(user1);

        }
        
        
        for (int i = 10; i < 14; i++) {
            User user1 = new User();
            user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
            user1.addSpecialization(SPECIALIZATION_CARDIOLOG);
            user1.setUsername("medic_" + i);
            user1.setEncryptedPassword(passwordEncoder.encode("pass"));
            user1.setFirstName("Name" + i);
            user1.setLastName("LastName" + i);
            user1.setEmail(i + "medic@test.com");
            user1.setPhoneNumber("074800000" + i);
//            userRepository.save(user1);
//            usersSpecializationsRepository.save(new UsersSpecializations(user1, SPECIALIZATION_CARDIOLOG));
            userList.add(user1);
            usersSpechsList.add(new UsersSpecializations(user1, SPECIALIZATION_CARDIOLOG));

        }
        for (int i = 14; i < 17; i++) {
            User user1 = new User();
            user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
            user1.addSpecialization(SPECIALIZATION_RADIOLOG);
            user1.setUsername("medic_" + i);
            user1.setEncryptedPassword(passwordEncoder.encode("pass"));
            user1.setFirstName("Name" + i);
            user1.setLastName("LastName" + i);
            user1.setEmail(i + "medic@test.com");
            user1.setPhoneNumber("074800000" + i);
//            userRepository.save(user1);
//            usersSpecializationsRepository.save(new UsersSpecializations(user1, SPECIALIZATION_RADIOLOG));

            userList.add(user1);
            usersSpechsList.add(new UsersSpecializations(user1, SPECIALIZATION_RADIOLOG));
        }
        for (int i = 17; i < 23; i++) {
            User user1 = new User();
            user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
            user1.addSpecialization(SPECIALIZATION_STOMATOLOG);
            user1.setUsername("medic_" + i);
            user1.setEncryptedPassword(passwordEncoder.encode("pass"));
            user1.setFirstName("Name" + i);
            user1.setLastName("LastName" + i);
            user1.setEmail(i + "medic@test.com");
            user1.setPhoneNumber("074800000" + i);
//            userRepository.save(user1);
//            usersSpecializationsRepository.save(new UsersSpecializations(user1, SPECIALIZATION_STOMATOLOG));
            userList.add(user1);
            usersSpechsList.add(new UsersSpecializations(user1, SPECIALIZATION_STOMATOLOG));
        }

        userRepository.save(userList);
        usersSpecializationsRepository.save(usersSpechsList);

    }

    public static final Specialization SPECIALIZATION_CARDIOLOG = new Specialization("Cardiolog");
    public static final Specialization SPECIALIZATION_RADIOLOG = new Specialization("Radiolog");
    public static final Specialization SPECIALIZATION_STOMATOLOG = new Specialization("Stomatolog");

}
