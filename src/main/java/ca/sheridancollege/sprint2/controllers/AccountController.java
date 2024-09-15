package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @PostMapping("/changeUserInfo")
    public String changeUserInfo(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "phone") long phone,
            @RequestParam(name = "secondaryEmail", required = false, defaultValue = "") String secondaryEmail,
            @RequestParam(name = "province") String province,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "postalCode", required = false, defaultValue = "") String postalCode,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        if (da.findUserAccount(auth.getName()) != null) {
            model.addAttribute("userExists", true);
        }
        return "/myAccount";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(
            @RequestParam("newEmail") String newEmail,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();

        if (newEmail.equals(currentEmail)) {
            model.addAttribute("error", "New email is same as the old email");
            return "/myAccount";
        }
        Boolean emailUpdated = da.updateUserEmail(currentEmail, newEmail);
        if (emailUpdated) {
            model.addAttribute("Message", "Your email has been changed successfully.");
        } else {
            model.addAttribute("error", "There was a problem updating your email.");
        }

        return "/myAccount";

    }


}




