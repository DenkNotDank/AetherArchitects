package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;


@Controller
public class HomeController {

    @Autowired
            @Lazy
    DatabaseAccess da;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("pageContent", da.getContent(1));
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(){
        return "login";
    }

//    @GetMapping("/register")
//    public String getRegister(){
//        return "register";
//    }

    @GetMapping("/myAccount")
    public String getMyAccountPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            System.out.println("User is " + auth.getName());
        }
        model.addAttribute("username", auth.getName());
        return "myAccount";
    }

    @PostMapping("/changePassword")
    public String changePassword(@RequestParam(name = "newPassword") String newPassword,
                                 @RequestParam(name = "confirmPassword") String confirmPassword){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (newPassword.equals(da.findUserPassword(auth.getName()))) {
            return "/error/changingPassword";
        }
        else{
            if(newPassword.equals(confirmPassword)){
                da.updateUserLogin(newPassword, auth.getName());
                return "login";
            }
            else{
                return "/error/changingPassword";
            }
        }

    }


    @GetMapping("/accessDenied")
    public String goError(){
        return "/error/accessDenied";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam(name = "email") String email,
                                @RequestParam(name="firstName") String firstName,
                                  @RequestParam(name = "lastName") String lastName,
                                  @RequestParam String password,
                                  @RequestParam(name= "phone") long phone,
                                  @RequestParam(name = "secondaryEmail", required = false, defaultValue = "") String secondaryEmail,
                                  @RequestParam (name = "province") String province,
                                  @RequestParam (name = "city") String city,
                                  @RequestParam(name = "postalCode",required = false, defaultValue = "") String postalCode,
                                  Model model){

        if(da.findUserAccount(email)!=null){
            model.addAttribute("userExists", true);
            return "login";
        }
        else{
            //Create new user database object
            da.createNewUser(email, firstName, lastName, phone, secondaryEmail, province, city, postalCode, password);
            long userId = da.findUserAccount(email).getUserId();
            //Add user roles to database. roleId=2 means they are a user, not an admin
            da.addRole(userId, 2);
            return "redirect:/";
        }


    }




}
