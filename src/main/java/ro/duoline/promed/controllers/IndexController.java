package ro.duoline.promed.controllers;

import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import ro.duoline.promed.domains.json.JsonEvent;

@Controller
public class IndexController {

    
    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("time", new Date());
        return "index";
    }

//    @CrossOrigin
    @GetMapping("/rest")
    @ResponseBody
    public List<JsonEvent> rest(Model model) {

        List<JsonEvent> objs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            JsonEvent obj = new JsonEvent();
            obj.setTitle("obj " + i);
            obj.setStart("2018-01-0" + i + "T1" + i + ":00:00");
            obj.setEnd("2018-01-0" + i + "T1" + i + ":30:00");

            objs.add(obj);
        }
        return objs;
    }

//    @CrossOrigin
//    @PostMapping("/rest")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public void restPost(@RequestBody JsonEvent data) {
//        System.out.println("IndexController.restPost(" + data + ")");
//    }

}
