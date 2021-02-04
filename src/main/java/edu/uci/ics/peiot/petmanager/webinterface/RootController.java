package edu.uci.ics.peiot.petmanager.webinterface;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/")
public class RootController {

    @GetMapping
    public String index() {
        return "Greetings from PET-Manager In PE-IoT System!";
    }



}