package com.milsondev.milsondev.controller;

import com.milsondev.milsondev.db.entities.Article;
import com.milsondev.milsondev.db.entities.User;
import com.milsondev.milsondev.service.ArticleService;
import com.milsondev.milsondev.service.CommentService;
import com.milsondev.milsondev.service.SubscriberService;
import com.milsondev.milsondev.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/admin")
public class AdminSubscriberController {

    private final SubscriberService subscriberService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminSubscriberController.class);

    @Autowired
    public AdminSubscriberController(final SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @RequestMapping(value = "/export-subscriber-to-csv", method = RequestMethod.GET)
    public String subscriberExportToCSV(HttpServletResponse servletResponse) throws IOException {
        servletResponse.setContentType("text/csv");
        servletResponse.addHeader("Content-Disposition","attachment; filename=\"subscribers.csv\"");
        subscriberService.exportToCSV(servletResponse.getWriter());
        // Cannot call sendRedirect() after the response has been committed
        // create a new thread to finish this job
        return "redirect:/admin";
    }

    @RequestMapping(value = "/export-subscriber-to-pdf", method = RequestMethod.GET)
    public String subscriberExportToPDF() {
        subscriberService.exportToPDF();
        return "redirect:/admin";
    }

    @RequestMapping(value = "/enable-disable-subscriber/{id}", method = RequestMethod.GET)
    public String enableDisableSubscriber(@PathVariable long id) {
        subscriberService.enableDisableSubscriber(id);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/delete-subscriber/{id}", method = RequestMethod.GET)
    public String deleteSubscriber(@PathVariable long id) {
        subscriberService.deleteSubscriber(id);
        return "redirect:/admin";
    }

}
