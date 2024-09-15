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
                                 Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        if (da.findUserAccount(auth.getName()) != null) {
            model.addAttribute("infoUpdated", false);
            return "/myAccount";
        }
        else{
            boolean updated = da.updateUserInfo(auth.getName(),firstName,lastName,phone,province,city,postalCode,secondaryEmail);
            model.addAttribute("infoUpdated", updated);
        }
        return "/myAccount";
    }


}
