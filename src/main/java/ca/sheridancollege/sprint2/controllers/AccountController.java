package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AccountController {

    @Autowired
    @Lazy
    private DatabaseAccess da;

    @GetMapping("/accessDenied")
    public String goError() {
        return "/error/accessDenied";
    }

    @GetMapping("/myAccount")
    public String getMyAccountPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            String email = auth.getName();
            User user = da.findUserAccount(email);
            System.out.println("Fetched user: " + user);
            if (user != null) {
                model.addAttribute("user", user);
            } else {
                model.addAttribute("error", "User not found.");
            }
        } else {
            model.addAttribute("error", "Authentication failed.");
        }
        model.addAttribute("isTab1Active", true);
        model.addAttribute("isTab2Active", false);
        return "myAccount";
    }

    @PostMapping("/changeUserInfo")
    public String changeUserInfo(
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName,
            @RequestParam(name = "phone") long phone,
            @RequestParam(name = "secondaryEmail", required = false, defaultValue = "") String secondaryEmail,
            @RequestParam(name = "province") String province,
            @RequestParam(name = "city") String city,
            @RequestParam(name = "postalCode", required = false, defaultValue = "") String postalCode,
            Model model,
            RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());
        if (da.findUserAccount(auth.getName()) == null) {
            model.addAttribute("infoUpdated", false);
            return "/myAccount";
        } else {
            boolean updated = da.updateUserInfo(auth.getName(), firstName, lastName, phone, province, city, postalCode,
                    secondaryEmail);
            redirectAttrs.addFlashAttribute("infoUpdated", updated);
        }
        return "redirect:/myAccount";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(
            @RequestParam("newEmail") String newEmail,
            Model model, RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentEmail = auth.getName();
        redirectAttrs.addFlashAttribute("isTab1Active", false);
        redirectAttrs.addFlashAttribute("isTab2Active", true);
        if (newEmail.equals(currentEmail)) {
            redirectAttrs.addFlashAttribute("error", "New email is same as the old email");
            return "redirect:/myAccount";
        }
        Boolean emailUpdated = da.updateUserEmail(currentEmail, newEmail);
        if (emailUpdated) {
            redirectAttrs.addFlashAttribute("Message", "Your email has been changed successfully.");
        } else {
            redirectAttrs.addFlashAttribute("error", "There was a problem updating your email.");
        }
        return "redirect:/myAccount";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "confirmPassword") String confirmPassword, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = da.findUserAccount(email);
        if (user == null) {
            model.addAttribute("error", "User not found.");
            return "login";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(newPassword, user.getEncryptedPassword())) {
            model.addAttribute("error", "New password cannot be the same as the current password.");
            return "/myAccount";  // Show error on the account page
        }
        if (newPassword.equals(confirmPassword)) {
            da.updateUserLogin(newPassword, email);
            model.addAttribute("message", "Password has been changed successfully!");
            return "login";  // Redirect to login after password change
        } else {
            model.addAttribute("error", "Passwords do not match.");
            return "/myAccount";
        }
    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        try {
            da.deleteUser(email);
            redirectAttrs.addFlashAttribute("accountDeleted", true);
            SecurityContextHolder.clearContext(); // Clear the user session
        } catch (Exception e) {
            // Print the error to the console
            System.out.println("Error deleting account for user: " + email);
            System.out.println(e.getMessage());

            redirectAttrs.addFlashAttribute("accountDeleted", false);
            // Add an error message attribute for display
            redirectAttrs.addFlashAttribute("error",
                    "There was a problem deleting your account. Please try again later.");
        }
        return "redirect:/";
    }

    @PostMapping("/selectMembership")
    public String selectMembership(@RequestParam("membershipType") String MembershipType, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = da.findUserAccount(email);

        if (user == null) {
            model.addAttribute("error", "There was a problem finding your account.");
            return "/myAccount";
        }
        return "redirect:/myAccount";
    }
}