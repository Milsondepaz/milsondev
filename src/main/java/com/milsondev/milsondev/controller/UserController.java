package com.milsondev.milsondev.controller;


import com.milsondev.milsondev.dtos.UserDTO;
import com.milsondev.milsondev.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @ModelAttribute("user")
    public UserDTO userRegistrationDto() {
        return new UserDTO();
    }

    @GetMapping
    public String showRegistrationForm() {
        return "registration";
    }


    @PostMapping
    public String registerUserAccount(@ModelAttribute("user") UserDTO userDTO) {
        System.out.println(userDTO.toString());
        userServiceImpl.save(userDTO);
        return "redirect:/registration?success";
    }


}
