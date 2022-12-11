package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping
public class SignUpContoller {

    private final UserService userService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SignUpContoller.class);

    @Autowired public SignUpContoller(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/sign-up")
    public String signup() {
        return "sign-up";
    }

    @RequestMapping(value = "/sign-up", method = RequestMethod.POST)
    public String addUser(@Valid @ModelAttribute("user") User user, Errors errors, RedirectAttributes attributes) {
        if (errors.hasErrors()){
            attributes.addFlashAttribute("mensagem_erro", "Please fill out all necessary fields correctly!");
            return "redirect:/sign-up";
        }

        if (!user.getPassword().equals(user.getRepeatPassword())){
            attributes.addFlashAttribute("mensagem_erro", "The password dont fit!");
            return "redirect:/sign-up";
        }

        userService.save(user);
        attributes.addFlashAttribute("mensagem", "You have successfully registered, click to ");

        LOGGER.info("Log - user sign-up {}", user.toString());

        return "redirect:/sign-up";
    }
}
