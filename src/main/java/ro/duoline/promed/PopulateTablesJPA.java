package ro.duoline.promed;

import ro.duoline.promed.jpa.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.jpa.RoleRepository;
import ro.duoline.promed.jpa.UserRepository;

/**
 * @author I.T.W764
 */
@Component
public class PopulateTablesJPA implements ApplicationListener<ContextRefreshedEvent> {

    @Resource(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

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
        loadUsersAndUsers();
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
            synchronized (this) {
                User user1 = new User();
                user1.addAuthority(SecurityConfig.AUTHORITY_PACIENT);
                user1.setUsername("pacient_" + i);
                user1.setEncryptedPassword(passwordEncoder.encode("password"));
                user1.setFirstName("Name" + i);
                user1.setLastName("LastName" + i);
                user1.setEmail(i + "pacient@test.com");
                user1.setPhoneNumber("074800000" + i);
                userRepository.save(user1);
            }
        }
        for (int i = 10; i < 14; i++) {
            synchronized (this) {
                User user1 = new User();
                user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
                user1.addSpecialization(SPECIALIZATION_CARDIOLOG);
                user1.setUsername("medic_" + i);
                user1.setEncryptedPassword(passwordEncoder.encode("pass"));
                user1.setFirstName("Name" + i);
                user1.setLastName("LastName" + i);
                user1.setEmail(i + "medic@test.com");
                user1.setPhoneNumber("074800000" + i);
                userRepository.save(user1);
            }
        }
        for (int i = 14; i < 17; i++) {
            synchronized (this) {
                User user1 = new User();
                user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
                user1.addSpecialization(SPECIALIZATION_RADIOLOG);
                user1.setUsername("medic_" + i);
                user1.setEncryptedPassword(passwordEncoder.encode("pass"));
                user1.setFirstName("Name" + i);
                user1.setLastName("LastName" + i);
                user1.setEmail(i + "medic@test.com");
                user1.setPhoneNumber("074800000" + i);
                userRepository.save(user1);
            }
        }
        for (int i = 17; i < 20; i++) {
            synchronized (this) {
                User user1 = new User();
                user1.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
                user1.addSpecialization(SPECIALIZATION_STOMATOLOG);
                user1.setUsername("medic_" + i);
                user1.setEncryptedPassword(passwordEncoder.encode("pass"));
                user1.setFirstName("Name" + i);
                user1.setLastName("LastName" + i);
                user1.setEmail(i + "medic@test.com");
                user1.setPhoneNumber("074800000" + i);
                userRepository.save(user1);
            }
        }

    }

    public static final Specialization SPECIALIZATION_CARDIOLOG = new Specialization("Cardiolog");
    public static final Specialization SPECIALIZATION_RADIOLOG = new Specialization("Radiolog");
    public static final Specialization SPECIALIZATION_STOMATOLOG = new Specialization("Stomatolog");

}
