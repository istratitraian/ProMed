package ro.duoline.promed;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import ro.duoline.promed.jpa.SpecializationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ro.duoline.promed.domains.DayTimeEvent;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.enums.EventStatus;
import ro.duoline.promed.jpa.DateTimeEventRepository;
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

    @Autowired
    private DateTimeEventRepository dateTimeEventRepository;

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
        roleRepository.save(SecurityConfig.AUTHORITY_CLIENT);
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
            user1.addAuthority(SecurityConfig.AUTHORITY_CLIENT);
            user1.setUsername("pacient_" + i);
            user1.setEncryptedPassword(passwordEncoder.encode("pass"));
            user1.setFirstName("Name" + i);
            user1.setLastName("LastName" + i);
            user1.setEmail(i + "pacient@test.com");
            user1.setPhoneNumber("074800000" + i);
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
            userList.add(user1);
            usersSpechsList.add(new UsersSpecializations(user1, SPECIALIZATION_STOMATOLOG));
        }

        userRepository.save(userList);
        usersSpecializationsRepository.save(usersSpechsList);

        LocalDate localDate = LocalDate.now();//today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        if (localDate.getDayOfWeek().getValue() == 7) {
            localDate = localDate.plusDays(1);
        } else if (localDate.getDayOfWeek().getValue() == 6) {
            localDate = localDate.plusDays(2);
        }
        String pattern = "yyyy-MM-dd";

        addDayEventsToMedic(13, 2, localDate.format(DateTimeFormatter.ofPattern(pattern)));
        addDayEventsToMedic(13, 2, localDate.plusDays(1).format(DateTimeFormatter.ofPattern(pattern)));
        addDayEventsToMedic(13, 2, localDate.plusDays(2).format(DateTimeFormatter.ofPattern(pattern)));
        addDayEventsToMedic(13, 2, localDate.plusDays(3).format(DateTimeFormatter.ofPattern(pattern)));
        addDayEventsToMedic(13, 2, localDate.plusDays(4).format(DateTimeFormatter.ofPattern(pattern)));

        addDayEventsToMedic(12, 2, "2018-01-01");
        addDayEventsToMedic(12, 2, "2018-01-02");

        addDayEventsToMedic(12, 2, "2018-03-01");
        addDayEventsToMedic(12, 2, "2018-04-01");
        addDayEventsToMedic(12, 2, "2018-05-01");

    }

    public static final Specialization SPECIALIZATION_CARDIOLOG = new Specialization("Cardiolog");
    public static final Specialization SPECIALIZATION_RADIOLOG = new Specialization("Radiolog");
    public static final Specialization SPECIALIZATION_STOMATOLOG = new Specialization("Stomatolog");

    private void addDayEventsToMedic(int medicId, int pacientId, String day) {

        try {
            User medic = userRepository.findOne(medicId);
            User client = userRepository.findOne(pacientId);
            medic.addClient(client);
            userRepository.save(medic);
            List<DayTimeEvent> dateEvents = new ArrayList<>();
            java.text.Format dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            java.util.Date date = (java.util.Date) dateFormat.parseObject(day + " 09:00");
            long time30min = (30 * 60 * 1000);
            DayTimeEvent event = new DayTimeEvent();
            event.setUser(medic);
            event.setClient(client);
            event.setDescription("Pacient1 Descriere");
            event.setStatus(EventStatus.REZERVED);
            Date start = new Date(date.getTime());
            Date end = new Date(start.getTime() + time30min);
            event.setStartDate(start);
            event.setEndDate(end);
            dateEvents.add(event);

            for (int i = 1; i < 5; i++) {
                event = new DayTimeEvent();
                event.setDescription("Pacient" + i + " Descriere");
                event.setStatus(EventStatus.REZERVED);
                start = new Date(end.getTime() + time30min);
                end = new Date(start.getTime() + time30min);
                event.setStartDate(start);
                event.setEndDate(end);
                event.setUser(medic);
                event.setClient(client);
                dateEvents.add(event);
            }

            dateTimeEventRepository.save(dateEvents);
        } catch (ParseException ex) {

            System.out.println("PopulateDB ERROR : " + ex);
        }
    }

}
