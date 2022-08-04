package com.milsondev.milsondev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping
public class Principal {

    private String address= "Favendo GmbH, Kirschäckerstraße 25, 96052 Bamberg, Germany";

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("address", address);
        return "index";
    }

    @GetMapping("/locale")
    public String changeLocale(@RequestParam String language, Model model) {
        model.addAttribute("address", address);
        return "index";
    }

}
