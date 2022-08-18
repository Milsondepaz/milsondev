package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.dtos.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class Principal {


    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }

    //1- spring secuirity

    // profile
    // edite all informartion on profile

    // profile blog area

    // list post
    // create new post
    // edit and delete


    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ModelAndView login(@ModelAttribute("user") User user) {
        System.out.println(user.toString());
        return new ModelAndView("index");
    }





}
