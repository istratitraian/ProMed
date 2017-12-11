package ro.duoline.promed.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import ro.duoline.promed.domains.json.JsonObj;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("time", new Date());
        return "index";
    }


    @GetMapping("/rest")
    @ResponseBody
    public JsonObj rest(Model model) {
        JsonObj obj = new JsonObj();
        obj.setId("1A");
        obj.setName("Name SecondName");
        return obj;
    }

}
