package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.Member;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    @Lazy
    public DatabaseAccess da;

    @GetMapping("/admin/dashboard")
    public String getAdminHome(){return "secure/admin/adminHome";
    }

    @GetMapping("/admin/emails")
    public String getAdminEmails(){
    return "secure/admin/emails";
}


    @GetMapping("/admin/members")
    public String getMembersPage(Model model){
        List<Member> members = da.getAllMembersInfo();
        model.addAttribute("members", members);
        return "/secure/admin/members";
    }

    @PostMapping("/filter")
    public String getEmails(
            @RequestParam(defaultValue = "false",name = "free") boolean free,
            @RequestParam(defaultValue = "false",name = "basic") boolean basic,
            @RequestParam(defaultValue = "false",name = "premium") boolean premium,
            @RequestParam(defaultValue = "false",name = "paid") boolean paid,
            @RequestParam(defaultValue = "false",name = "unpaid") boolean unpaid,
            @RequestParam(defaultValue = "false",name = "admin") boolean admin,
            @RequestParam(defaultValue = "false",name = "user") boolean user,
            @RequestParam(defaultValue = "false",name = "suspended") boolean suspended,
            @RequestParam(defaultValue = "false",name = "notSuspended") boolean notSuspended,
            @RequestParam(defaultValue = "false",name = "secondary") boolean secondary,
            Model model){

       List<Member> emails = da.getFilteredList(free, basic, premium, paid, unpaid,
               admin, user, suspended, notSuspended, secondary);

        String csvEmails;
       if(emails.isEmpty()){
           System.out.println("emails is empty");
           csvEmails="There are no results";
           model.addAttribute("filteredEmails", csvEmails);
           return "/secure/admin/emails";
       }
        System.out.println(emails.size());

       List<String> filteredEmails = new ArrayList<>();
        if(secondary){
            for (Member secondaryEmail : emails) {
                filteredEmails.add(secondaryEmail.getSecondaryEmail());
            }
        }
        else{
            for (Member email : emails) {
                filteredEmails.add(email.getEmail());
            }
        }


        csvEmails = String.join(",", filteredEmails);

        model.addAttribute("filteredEmails", csvEmails);

        return "/secure/admin/emails";
    }


}
