/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.milsondev.milsondev.exceptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @Override
    public String getErrorPath() {

        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        String errorPage = "error";

        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                // handle HTTP 404 Not Found error
                errorPage = "error/404";
                model.addAttribute("errorNotFound", "Error 404: Page Not Found");

            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                // handle HTTP 400 Bad Request error
                errorPage = "error/400";
                model.addAttribute("errorBadRequest", "Error 400: Bad Request");


            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                // handle HTTP 403 Forbidden error
                errorPage = "error/403";
                model.addAttribute("errorForbidden", "Error 403: Forbidden");

            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                // handle HTTP 500 Internal Server error
                errorPage = "error/500";
                model.addAttribute("errorInternalServer", "Error 500: Internal Server error");


            }
        }


        errorPage = "error";

        return errorPage;
    }

}
