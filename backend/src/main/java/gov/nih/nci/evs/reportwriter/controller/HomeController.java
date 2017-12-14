package gov.nih.nci.evs.reportwriter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HomeController {


        @RequestMapping(value = "/", method = RequestMethod.GET)
        public @ResponseBody String home() {
                return "Welcome to ReportWriter";
        }


}
