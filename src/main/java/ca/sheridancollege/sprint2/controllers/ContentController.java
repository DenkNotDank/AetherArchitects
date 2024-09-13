package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.Content;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ContentController {

    @Autowired
    @Lazy
    public
    DatabaseAccess da;

    @PostMapping("/savecontent")
    public String saveContent(@RequestBody Content content, Model model){
//        System.out.println(content.getContentId());
//        System.out.println(content.getContentBody());


        boolean result = da.saveContent(content.getContentId(), content.getContentBody());
        model.addAttribute("saveResult", result);
        return "index";
    }

}
