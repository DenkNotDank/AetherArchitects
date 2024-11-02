package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    @Lazy
    DatabaseAccess da;

    @GetMapping("/")
    public String getHome(Model model) {
        da.remoteDBTest();
        model.addAttribute("pageContent", da.getContent(1));
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/register")
    public String processRegister(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam String password,
            @RequestParam(name = "phone") long phone,
            @RequestParam(name = "secondaryEmail", required = false, defaultValue = "") String secondaryEmail,
            @RequestParam(name = "province") String province,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "postalCode", required = false, defaultValue = "") String postalCode,
            Model model) {

        if (da.findUserAccount(email) != null) {
            model.addAttribute("userExists", true);
            return "login";
        } else {
            // Create new user database object
            da.createNewUser(email, firstName, lastName, phone, secondaryEmail, province, city, postalCode, password);
            long userId = da.findUserAccount(email).getUserId();
            // Add user roles to database. roleId=2 means they are a user, not an admin
            da.addRole(userId, 2);
            return "redirect:/";
        }
    }
}