package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {
    
    @Autowired
    @Lazy
    DatabaseAccess da;

    @GetMapping("/chapters")
    public String getChaptersPage(Model model) {
        model.addAttribute("pageContent", da.getContent(2));
        model.addAttribute("pageHidden", da.isPageHidden(2));
        return "pages/aboutUsPages/chapters";
    }

    @GetMapping("/publications")
    public String getPublicationsPage(Model model) {
        model.addAttribute("pageContent", da.getContent(3));
        model.addAttribute("pageHidden", da.isPageHidden(3));
        return "pages/aboutUsPages/publications";
    }

    @GetMapping("/staff")
    public String getStaffPage(Model model) {
        model.addAttribute("pageContent", da.getContent(4));
        model.addAttribute("pageHidden", da.isPageHidden(4));
        return "pages/aboutUsPages/staff";
    }

    @GetMapping("/sponsor")
    public String getSponsorPage(Model model) {
        model.addAttribute("pageContent", da.getContent(5));
        model.addAttribute("pageHidden", da.isPageHidden(5));
        return "pages/aboutUsPages/sponsor";
    }

    @GetMapping("/donate")
    public String getDonatePage(Model model) {
        model.addAttribute("pageContent", da.getContent(6));
        model.addAttribute("pageHidden", da.isPageHidden(6));
        return "pages/donate";
    }

    @GetMapping("/parents")
    public String getParentsPage(Model model) {
        model.addAttribute("pageContent", da.getContent(7));
        model.addAttribute("pageHidden", da.isPageHidden(7));
        return "pages/ourPrograms/parents";
    }

    @GetMapping("/childrenYouth")
    public String getChildrenYouthPage(Model model) {
        model.addAttribute("pageContent", da.getContent(8));
        model.addAttribute("pageHidden", da.isPageHidden(8));
        return "pages/ourPrograms/childrenYouth";
    }

    @GetMapping("/earlyYears")
    public String getEarlyYearsPage(Model model) {
        model.addAttribute("pageContent", da.getContent(9));
        model.addAttribute("pageHidden", da.isPageHidden(9));
        return "pages/ourPrograms/earlyYears";
    }

    @GetMapping("/educators")
    public String getEducatorsPage(Model model) {
        model.addAttribute("pageContent", da.getContent(10));
        model.addAttribute("pageHidden", da.isPageHidden(10));
        return "pages/ourPrograms/educators";
    }
    
    @GetMapping("/camp")
    public String getCampPage(Model model) {
        model.addAttribute("pageContent", da.getContent(11));
        model.addAttribute("pageHidden", da.isPageHidden(11));
        return "pages/camp";
    }
}
