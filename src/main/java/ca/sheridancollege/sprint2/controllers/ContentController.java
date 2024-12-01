package ca.sheridancollege.sprint2.controllers;

import ca.sheridancollege.sprint2.beans.Content;
import ca.sheridancollege.sprint2.database.DatabaseAccess;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @PostMapping("/uploadMedia")
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile file) {
        String dir = "src/main/resources/static/uploads/";
        String type = file.getContentType();
        List<String> allowedMimeTypes = List.of("image/jpeg", "image/png", "image/gif");

        if (!allowedMimeTypes.contains(type)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: Only image files (JPEG, PNG, GIF) are allowed.");
        }

        int maxTotalFilesAllowed = 15;
        File directory = new File(dir);
        if (directory.exists() && directory.isDirectory()) {
            int totalFiles = Objects.requireNonNull(directory.list()).length;

            if (totalFiles >= maxTotalFilesAllowed) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: The upload folder already contains the maximum allowed number of files.");
            }
        }

        try {
            if (file.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: No file selected.");
            }

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(dir + fileName);
            Files.write(filePath, file.getBytes());

            String fileUrl = "/uploads/" + fileName;
            return ResponseEntity.ok(Map.of("location", fileUrl));

        } catch (MaxUploadSizeExceededException e) {
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("Error: The file size exceeds the allowed limit.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed due to an internal error.");
        }
    }



}
