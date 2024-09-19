package ca.sheridancollege.sprint2.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.database.DatabaseAccess;

@Controller
public class AdminController {

    @Autowired
    @Lazy
    DatabaseAccess da;

    @GetMapping("/admin/dashboard")
    public String getAdminHome(Model model){
        List<User> userList = da.getAllUsers();
        model.addAttribute("userList", userList);
        return "secure/admin/adminHome";
    }

    @PostMapping("/adminDeleteAccount")
    public String adminDeleteAccount(Model model, @RequestParam("userId") long userId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUserEmail = auth.getName();

        String userEmail = da.getEmailById(userId);
        boolean isOnlyAdmin = da.isOneAdmin();

        if (isOnlyAdmin && userEmail.equals(currentUserEmail)) {
            model.addAttribute("onlyAdmin", true);
            List<User> userList = da.getAllUsers();
            model.addAttribute("userList", userList);
            System.out.println("Unable to delete the only admin.");
            return "secure/admin/adminHome";
        }

        da.deleteUserById(userId);

        if (userEmail.equals(currentUserEmail)) {
            SecurityContextHolder.clearContext();
            return "redirect:/";
        } else {
            return "redirect:/admin/dashboard";
        }
    }
}
