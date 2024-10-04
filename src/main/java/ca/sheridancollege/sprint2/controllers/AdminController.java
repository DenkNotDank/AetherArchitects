package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.Member;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    @Lazy
    DatabaseAccess da;

    @GetMapping("/admin/dashboard")
    public String getAdminHome(){
        return "secure/admin/adminHome";
    }


    @GetMapping("/admin/emails")
    public String getAdminEmails(){
    return "secure/admin/Emails";
}


    @GetMapping("/admin/members")
    public String getMembersPage(Model model){
        List<Member> members = da.getAllMembersInfo();
        model.addAttribute("members", members);
        return "/secure/admin/members";
    }

}
