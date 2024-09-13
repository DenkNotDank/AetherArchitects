package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {

    @Autowired
    @Lazy
    DatabaseAccess da;

    @GetMapping("/admin/dashboard")
    public String getAdminHome(){
        return "secure/admin/adminHome";
    }
}
