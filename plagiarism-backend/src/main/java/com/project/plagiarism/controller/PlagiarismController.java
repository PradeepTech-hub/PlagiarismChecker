package com.project.plagiarism.controller;

import com.project.plagiarism.service.PlagiarismService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class PlagiarismController {

    private final PlagiarismService service;

    public PlagiarismController(PlagiarismService service) {
        this.service = service;
    }

    // No dataset parameter anymore
    @GetMapping("/check")
    public double checkPlagiarism(
            @RequestParam("text") String text
    ) throws Exception {
        return service.checkAgainstAll(text);
    }
}
