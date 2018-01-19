/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.duoline.promed.controllers;

import java.io.IOException;
import java.security.Principal;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import ro.duoline.promed.SecurityConfig;
import ro.duoline.promed.converters.EventsToJson;
import ro.duoline.promed.commands.UserMedicForm;
import ro.duoline.promed.controllers.pagenav.PageNav;
import ro.duoline.promed.converters.UserFormToUser;
import ro.duoline.promed.converters.UserToUserForm;
import ro.duoline.promed.domains.DayTimeEvent;
import ro.duoline.promed.domains.Picture;
import ro.duoline.promed.domains.Specialization;
import ro.duoline.promed.domains.User;
import ro.duoline.promed.domains.UsersSpecializations;
import ro.duoline.promed.domains.json.JsonEvent;
import ro.duoline.promed.enums.EventStatus;
import ro.duoline.promed.jpa.DateTimeEventRepository;
import ro.duoline.promed.jpa.PictureRepository;
import ro.duoline.promed.jpa.RoleRepository;
import ro.duoline.promed.jpa.SpecializationRepository;
import ro.duoline.promed.jpa.UserRepository;
import ro.duoline.promed.jpa.UsersSpecializationsRepository;

/**
 *
 * @author I.T.W764
 */
@Controller
public class ServerController {

//    private Path path;
//
//    @Autowired
//    private MessageSource messageSource;
    @Autowired
    private RoleRepository roleRepository;

    @Resource(name = "editUserFormValidator")
    private Validator editUserFormValidator;

    @Resource(name = "newUserFormValidator")
    private Validator newUserFormValidator;

    @Resource(name = "passwordEncoder")
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserToUserForm userToUserForm;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFormToUser userFormToUser;

    @Autowired
    private SpecializationRepository specializationRepository;

    @Autowired
    private UsersSpecializationsRepository usersSpecializationsRepository;

    @Autowired
    private DateTimeEventRepository dateTimeEventRepository;
    public static final long SERVER_CLIENT_TIME = 1800000;//30 min in mls
    public static final int MAX_WORK_HOUR = 18;
    public static final int START_WORK_HOUR = 9;

    @CrossOrigin
    @GetMapping("/server/calendar/jsonrest/get/{eventId}")
    @ResponseBody
    public JsonEvent getJsonEvent(Principal principal, @PathVariable Integer eventId) {
//        List<DayTimeEvent> dateEvents = new ArrayList<>();
        if (principal != null) {
//            User user = userRepository.findByUsername(principal.getName());
            System.out.println("getJsonEvent(" + eventId + ")");

            return new JsonEvent(dateTimeEventRepository.findOne(eventId));
        }

        return null;
    }

    @GetMapping("/server/calendar/jsonrest")
    @ResponseBody
    public List<JsonEvent> getJsonEvents(Principal principal,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        List<DayTimeEvent> dateEvents = new ArrayList<>();
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            try {
                System.out.println("getJsonEvents "
                        + ", s:" + start.split("T")[0]
                        + " - " + end.split("T")[0] + ":e"
                        + " principal " + principal.getName());

                Format dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date sD = (Date) dateFormat.parseObject(start.split("T")[0]);
                Date eD = (Date) dateFormat.parseObject(end.split("T")[0]);

                dateEvents.addAll(dateTimeEventRepository.findByUserIdAndStartDateBetween(user.getId(), sD, eD));
            } catch (NumberFormatException | ParseException e) {
                System.out.println("ERROR ServerController.getCalendar(" + start + "), " + e);
            }
        }

        System.out.println("- - - - " + start + " dateEvents : " + dateEvents.size());

        return new EventsToJson(dateEvents).getJsonEvents();
    }

//    @CrossOrigin
    @GetMapping("/server/calendar/jsonclient/{serverId}")
    @ResponseBody
    public List<JsonEvent> getJsonClientEvents(
            @PathVariable Integer serverId,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) throws ParseException {

        List<DayTimeEvent> dateEvents = new ArrayList<>();

        User server = userRepository.findOne(serverId);
        if (server != null) {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date dStart = new Date();

            Date sD = (Date) dateFormat.parse(dateFormat.format(dStart));
            Date eD = (Date) dateFormat.parseObject(end.split("T")[0]);

            dateEvents.addAll(dateTimeEventRepository.findByUserIdAndStartDateBetween(server.getId(), sD, eD));

            Collections.sort(dateEvents, (DayTimeEvent o1, DayTimeEvent o2) -> {

                return o1.getStartDate().compareTo(o2.getStartDate());
            });

            List<DayTimeEvent> clientEvents = new ArrayList<>();

            System.out.println("dStart.getHours() = " + dStart.getHours());
            if (dateEvents.isEmpty()) {
                for (int i = 0; i < 20 && dStart.getHours() < MAX_WORK_HOUR && dStart.getHours() >= START_WORK_HOUR; i++) {
                    DayTimeEvent event = new DayTimeEvent();
                    event.setDescription("Consultatie Nume Prenume");
                    event.setStartDate(dStart);
                    Date dEnd = new Date(dStart.getTime() + SERVER_CLIENT_TIME);
                    event.setEndDate(dEnd);
                    event.setUser(server);
                    event.setStatus(EventStatus.ACTIVE);
                    clientEvents.add(event);
                    dStart = dEnd;
                }
            } else {
                Date dNextStart = dateEvents.get(0).getStartDate();
                long diff = dNextStart.getTime() - dStart.getTime();

                while (diff >= SERVER_CLIENT_TIME && dStart.getHours() < MAX_WORK_HOUR && dStart.getHours() >= START_WORK_HOUR) {
                    DayTimeEvent event = new DayTimeEvent();
                    event.setDescription("Consultatie Nume Prenume");
                    event.setStartDate(dStart);
                    Date dNextEnd = new Date(dStart.getTime() + SERVER_CLIENT_TIME);
                    event.setEndDate(dNextEnd);
                    event.setUser(server);
                    event.setStatus(EventStatus.ACTIVE);
                    clientEvents.add(event);
                    diff -= SERVER_CLIENT_TIME;
                    dStart = dNextEnd;
                }

                Date dNextEnd = dateEvents.get(0).getEndDate();

                for (int i = 1; i < dateEvents.size(); i++) {
                    dNextStart = dateEvents.get(i).getStartDate();
                    diff = dNextStart.getTime() - dNextEnd.getTime();
                    while (diff >= SERVER_CLIENT_TIME && dNextEnd.getHours() < MAX_WORK_HOUR && dNextEnd.getHours() >= START_WORK_HOUR) {
                        DayTimeEvent event = new DayTimeEvent();
                        event.setDescription("Consultatie Nume Prenume");
                        event.setStartDate(dNextEnd);
                        Date dEnd = new Date(dNextEnd.getTime() + SERVER_CLIENT_TIME);
                        event.setEndDate(dEnd);
                        event.setUser(server);
                        event.setStatus(EventStatus.ACTIVE);
                        clientEvents.add(event);
                        diff -= SERVER_CLIENT_TIME;
                        dNextEnd = dEnd;
                    }
                    dNextEnd = dateEvents.get(i).getEndDate();
                }
            }

            dateTimeEventRepository.save(clientEvents);

            clientEvents = new ArrayList<>();
            
            clientEvents.addAll(dateTimeEventRepository.findByUserIdAndStatusAndStartDateBetween(server.getId(),EventStatus.ACTIVE, sD, eD));
            
            System.out.println("- - - - " + sD + ", now = " + eD + " getJsonClientEvents : " + clientEvents.size() + ", dbActiveEvents = " + dateEvents.size());
            return new EventsToJson(clientEvents).getJsonEvents();
        }
        return null;
    }

    @PostMapping("/server/calendar/jsonclient/save/{serverId}")
//    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<Void> saveClientEvent(@RequestBody JsonEvent event, @PathVariable Integer serverId) throws ParseException {

        User server = userRepository.findOne(serverId);

        if (server != null) {

            DayTimeEvent dayTimeEvent = dateTimeEventRepository.findOne(event.getId());
            System.out.println("saveClientEvent(" + event + ") exists = " + (dayTimeEvent != null));
            User client = null;

            if (dayTimeEvent != null) {
                client = dayTimeEvent.getClient();
                dateTimeEventRepository.delete(event.getId());
            }
            dayTimeEvent = new DayTimeEvent();
            dayTimeEvent.setDescription(
                    String.format("%s%n%s %s%n%s%n%s",
                            event.getTitle(), event.getFirstName(), event.getLastName(),
                            event.getPhoneNumber(), event.getEmail()));

            Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date sD = (Date) dateFormat.parseObject(event.getStart());
            Date eD = (Date) dateFormat.parseObject(event.getEnd());
            dayTimeEvent.setStartDate(sD);
            dayTimeEvent.setEndDate(eD);
            dayTimeEvent.setStatus(EventStatus.REZERVED);
            dayTimeEvent.setUser(server);

            if (client == null) {
                client = new User();
                client.setPhoneNumber(event.getPhoneNumber());
                client.setEncryptedPassword(passwordEncoder.encode("pass"));
                String userName = event.getFirstName() + event.getLastName() + event.getId();
                client.setUsername(userName);
                client.setFirstName(event.getFirstName());
                client.setLastName(event.getLastName());
                client.addAuthority(SecurityConfig.AUTHORITY_CLIENT);
                client.setEmail(event.getEmail());
                System.out.println("new Client " + client);

            } else {
                System.out.println("existentClient " + client);
                client.setPhoneNumber(event.getPhoneNumber());
                client.setFirstName(event.getFirstName());
                client.setLastName(event.getLastName());
                client.setEmail(event.getEmail());
            }
            userRepository.save(client);

            dayTimeEvent.setClient(client);

            dateTimeEventRepository.save(dayTimeEvent);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
    }

//    @CrossOrigin
    @DeleteMapping("/server/calendar/jsonrest/delete")
    @ResponseStatus(value = HttpStatus.OK)
//    public ResponseEntity<Void> deleteEvent(@RequestBody JsonEvent ev, Principal principal) {
    public void deleteEvent(@RequestBody JsonEvent ev, Principal principal) {
        System.out.println("deleteEvent(" + ev.getId() + ")");
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            DayTimeEvent event = dateTimeEventRepository.findOne(ev.getId());

            if (Objects.equals(event.getUser().getId(), user.getId())) {

                System.out.println("user " + user.getUsername() + ", event = " + ev.getId() + " : DELETED");
                dateTimeEventRepository.delete(ev.getId());
//                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @CrossOrigin
    @PostMapping("/server/calendar/jsonrest/save")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveEvent(@RequestBody JsonEvent event, Principal principal) throws ParseException {

        if (principal != null) {

            DayTimeEvent dayTimeEvent = dateTimeEventRepository.findOne(event.getId());
            System.out.println("saveEvent(" + event + ") exists = " + (dayTimeEvent != null));
            User user = userRepository.findByUsername(principal.getName());
            if (dayTimeEvent != null) {
                dateTimeEventRepository.delete(event.getId());
            }
            dayTimeEvent = new DayTimeEvent();
            dayTimeEvent.setDescription(event.getTitle());

            Format dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date sD = (Date) dateFormat.parseObject(event.getStart());
            Date eD = (Date) dateFormat.parseObject(event.getEnd());
            dayTimeEvent.setStartDate(sD);
            dayTimeEvent.setEndDate(eD);
//            dayTimeEvent.setStart(event.getStart());
//            dayTimeEvent.setEnd(event.getEnd());
            dayTimeEvent.setStatus(EventStatus.REZERVED);
            dayTimeEvent.setUser(user);
//          dayTimeEvent.setClient(client);
            dateTimeEventRepository.save(dayTimeEvent);
//
        }

    }

    @GetMapping("/medic/info/{userName}")
    public String userInfo(@PathVariable String userName, Model model, Principal principal) {
        User user = userRepository.findByUsername(userName);
        boolean isAuthorized = principal != null
                && principal.getName().equals(user.getUsername())
                && user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC);
        model.addAttribute("isMedicAuthorized", isAuthorized);
        model.addAttribute("medic", user);
        return "medic/show";
    }

    private final PageNav pageNav = new PageNav(3, "/medic/list/");
    private List<User> userList;

    @GetMapping("/medic/list/{page}")
    public String list(Model model, @PathVariable(name = "page", required = false) Integer pageIndex) {
//        if (userList == null) {
        userList = new ArrayList<>(roleRepository.findByAuthority(SecurityConfig.AUTHORITY_MEDIC.getAuthority()).getUsers());
        sortUsersById(userList);
//        }

        model.addAttribute("medics", pageNav.buildPageNav(model, pageIndex, userList));
        return "medic/list";
    }

    public static List<User> sortUsersById(List<User> list) {
        Collections.sort(list, (User o1, User o2) -> o1.getId() > o2.getId() ? 1 : -1);
        return list;
    }

    @GetMapping("/medic/show/{id}")
    public String getUser(@PathVariable Integer id, Model model, Principal principal) {
        User user = userRepository.findOne(id);

        System.out.println("MedicController.getUser(" + id + ") user : " + user.getAuthorities()
                + ", isMEdic=" + user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC)
        );

        boolean isAuthorized = principal != null
                && principal.getName().equals(user.getUsername())
                && user.getAuthorities().contains(SecurityConfig.AUTHORITY_MEDIC);

        model.addAttribute("isMedicAuthorized", isAuthorized);
        model.addAttribute("medic", user);
        return "medic/show";
    }

    @GetMapping("/medic/edit/{id}")
//    @PreAuthorize("#id == authentication.name")
    public String editGet(@PathVariable Integer id, Model model, Principal principal) {
        User user = userRepository.findOne(id);
        boolean isAuthorized = principal.getName().equals(user.getUsername());
        if (!isAuthorized) {
            return "redirect:/access_denied";
        }
        UserMedicForm userMedicForm = new UserMedicForm(userToUserForm.convert(user));
        userMedicForm.setSpecialization(user.getUsersSpecializations().toString().replaceFirst("\\[", "").replaceFirst("\\]", ""));
        model.addAttribute("userMedicForm", userMedicForm);

        return "medic/medicformEdit";
    }

    @PostMapping("/medic/edit")
    public String editUser(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {
        editUserFormValidator.validate(userMedicForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "medic/medicformEdit";
        }
        return updateMedic(userMedicForm);
    }

    @GetMapping("/admin/newmedic")
    public String newMedic(Model model) {
        model.addAttribute("userMedicForm", new UserMedicForm());
        return "medic/medicform";
    }

    @PostMapping("/admin/newmedic")
    public String newMedic(@Valid UserMedicForm userMedicForm, BindingResult bindingResult) {
        newUserFormValidator.validate(userMedicForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "medic/medicform";
        }
        return saveMedic(userMedicForm);
    }

    @Autowired
    private PictureRepository pictureRepository;

    private String saveMedic(UserMedicForm userMedicForm) {

        String[] spechs = userMedicForm.getSpecialization().split(",");
        User u = userFormToUser.convert(userMedicForm);
        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        User savedUser = userRepository.save(u);
        MultipartFile file = userMedicForm.getFile();
        if (file != null && !file.isEmpty()) {
            Picture picture = new Picture();
            try {
                picture.setUser(u);
                picture.setImage(file.getBytes());
                pictureRepository.save(picture);
                u.setProfileImage(picture);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from uploaded file.");
            }
        }

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();

        for (String spech : spechs) {
            spech = spech.trim();
            Specialization specialization = specializationRepository.findByName(spech);
            if (specialization == null) {
                specialization = new Specialization(spech);
                specializationRepository.save(specialization);
            }

            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);
        return "redirect:/medic/show/" + savedUser.getId();
    }

    private String updateMedic(UserMedicForm userMedicForm) {

        String[] spechs = userMedicForm.getSpecialization().split(",");
        User u = userFormToUser.convert(userMedicForm);
        u.addAuthority(SecurityConfig.AUTHORITY_MEDIC);
        MultipartFile file = userMedicForm.getFile();
        User savedUser;
        if (file != null && !file.isEmpty()) {
            savedUser = userRepository.save(u);
            try {
                Picture picture = new Picture();
                picture.setUser(u);
                picture.setImage(file.getBytes());
                pictureRepository.save(picture);
                u.setProfileImage(picture);
            } catch (IOException e) {
                System.err.println("Unable to get byte array from uploaded file.");
            }
        } else {

            u.setProfileImage(pictureRepository.findByUserId(u.getId()));
            savedUser = userRepository.save(u);
        }

        Set<UsersSpecializations> usersSpecializations = new HashSet<>();

        for (String spech : spechs) {
            spech = spech.trim();
            Specialization specialization = specializationRepository.findByName(spech);
            if (specialization == null) {
                specialization = new Specialization(spech);
                specializationRepository.save(specialization);
            }

            usersSpecializations.add(new UsersSpecializations(savedUser, specialization));
        }

        usersSpecializationsRepository.save(usersSpecializations);
        return "redirect:/medic/show/" + savedUser.getId();
    }

//    private String getProperty(String prop) {
//        return messageSource.getMessage(prop, new Object[]{}, LocaleContextHolder.getLocale());
//    }
}
