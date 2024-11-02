package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.Content;
import ca.sheridancollege.sprint2.database.DatabaseAccess;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContentController {

    @Autowired
    @Lazy
    public DatabaseAccess da;

    @PostMapping("/togglePageVisibility")
    public ResponseEntity<String> togglePageVisibility(@RequestBody Map<String, Object> payload) {
        if (payload.get("contentId") == null || payload.get("hidden") == null) {
            return ResponseEntity.badRequest().body("Missing required parameters.");
        }
        Long contentId = Long.valueOf(payload.get("contentId").toString());
        boolean hidden = (Boolean) payload.get("hidden");

        boolean result = da.updatePageHiddenStatus(contentId, hidden);

        if (result) {
            return ResponseEntity.ok("Visibility updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update visibility.");
        }
    }

    @PostMapping("/savecontent")
    public String saveContent(@RequestBody Content content, Model model) {
        // System.out.println(content.getContentId());
        // System.out.println(content.getContentBody());

        boolean result = da.saveContent(content.getContentId(), content.getContentBody());
        model.addAttribute("saveResult", result);
        return "index";
    }

}
