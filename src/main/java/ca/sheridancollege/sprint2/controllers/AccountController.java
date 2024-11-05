package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.User;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Date;

import static java.lang.Integer.parseInt;

@Controller
public class AccountController {

    @Autowired
    @Lazy
    public DatabaseAccess da;

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
                String membershipType = da.getUserMembership(user.getUserId());
                model.addAttribute("membershipType", membershipType);
            } else {
                model.addAttribute("error", "User not found.");
                model.addAttribute("user", new User());
            }
        } else {
            model.addAttribute("error", "Authentication failed.");
            model.addAttribute("user", new User());
        }
        model.addAttribute("isTab1Active", true);
        model.addAttribute("isTab2Active", false);
        model.addAttribute("isTab3Active", false);
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
            return "redirect:/myAccount";
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
        Boolean emailUpdated = da.updateUserEmail(currentEmail, newEmail);
        if (emailUpdated) {
            redirectAttrs.addFlashAttribute("Message", "Your email has been changed successfully.");

            // Set user to new email
            Authentication newAuth = new UsernamePasswordAuthenticationToken(newEmail, auth.getCredentials(),
                    auth.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);

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
            return "redirect:/myAccount"; // Show error on the account page
        }
        if (newPassword.equals(confirmPassword)) {
            da.updateUserLogin(newPassword, email);
            model.addAttribute("message", "Password has been changed successfully!");
            return "login"; // Redirect to login after password change
        } else {
            model.addAttribute("error", "Passwords do not match.");
            return "redirect:/myAccount";
        }
    }

    @PostMapping("/markPaidSubmit")
    public String markPaidSubmit(@RequestParam("paidMemberList") String paidMemberList,
                                 @RequestParam("paidToggle") String paidToggle,
                                 @RequestParam("tier") String tier,
                                 @RequestParam("datePaid") String datePaid,
                                 RedirectAttributes redirectAttributes) {

        boolean updateSuccessful = da.updatePaidInfo(paidMemberList, paidToggle, tier, datePaid);

        if (updateSuccessful) {
            redirectAttributes.addFlashAttribute("successMessage", "User memberships updated successfully.");
        } else {
            System.out.println("nope");
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid user entered.");
        }

        return "redirect:/admin/members";
    }

    @PostMapping("/changeUserPassword")
    public String changeUserPassword(@RequestParam("email") String email,
                                      @RequestParam("newPassword") String newPassword,
                                      @RequestParam("confirmPassword") String confirmPassword,
                                      RedirectAttributes redirectAttrs) {
        // Validate the new passwords
        if (!newPassword.equals(confirmPassword)) {
            redirectAttrs.addFlashAttribute("errorMessage", "Passwords do not match.");
            return "redirect:/admin/members";
        }
    
        User user = da.findUserAccount(email);
        if (user == null) {
            redirectAttrs.addFlashAttribute("errorMessage", "User not found.");
            return "redirect:/admin/members";
        }
    
        // Check if the new password is the same as the current password
        if (new BCryptPasswordEncoder().matches(newPassword, user.getEncryptedPassword())) {
            redirectAttrs.addFlashAttribute("errorMessage", "New password cannot be the same as the current password.");
            return "redirect:/admin/members";
        }
    
        // Encode the password and update in the database
        boolean updated = da.updateUserLogin(newPassword, email);
        if(updated){
            redirectAttrs.addFlashAttribute("successMessage", "Password changed successfully for user: " + email);
            return "redirect:/admin/members";
        }
        else{
            redirectAttrs.addFlashAttribute("errorMessage", "System error: User password not updated");
            return "redirect:/admin/members";
        }

    }

    @PostMapping("/deleteAccount")
    public String deleteAccount(RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        try {
            boolean deleted = da.deleteUser(email);
            if (deleted) {
                redirectAttrs.addFlashAttribute("accountDeleted", true);
                SecurityContextHolder.clearContext();// Clear the user session
            }
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
    public String selectMembership(@RequestParam("membershipType") String MembershipType,
            RedirectAttributes redirectAttrs) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = da.findUserAccount(email); // retrieve the user object

        if (user == null) {
            redirectAttrs.addFlashAttribute("error", "There was a problem finding your account.");
            return "redirect:/myAccount";
        }
        long userId = user.getUserId();

        int membershipId = 0;
        Boolean paid = false;
        Date paidDate = null;

        if (MembershipType.equalsIgnoreCase("alumni")) {
            membershipId = 1;
        } else if (MembershipType.equals("general")) {
            membershipId = 2;
        } else if (MembershipType.equalsIgnoreCase("professional")) {
            membershipId = 3;
        } else {
            redirectAttrs.addFlashAttribute("error", "Invalid Membership Type");
            return "redirect:/myAccount";
        }

        try {
            da.updateUserMembership(userId, membershipId, paid, paidDate);
            redirectAttrs.addFlashAttribute("message", "Membership has been updated successfully.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "There was a problem updating your account.");
            return "redirect:/myAccount";
        }
        return "redirect:/myAccount";
    }

}