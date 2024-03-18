package main.java.com.example.demo_api.controller;

import com.example.demo_api.model.PromptRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import main.java.com.example.demo_api.service.OpenAIService;

@Controller
public class PromptController {

    private final OpenAIService openAiService;

    public PromptController(OpenAIService openAiService) {
        this.openAiService = openAiService;
    }

    @PostMapping("/prompt")
    public ResponseEntity<String> handlePrompt(@RequestBody PromptRequest request) {
        String prompt = request.getPrompt();
        String imageBase64 = request.getImageBase64();

        String openAIresponse = openAiService.callOpenAI(prompt,imageBase64);

        // Process the prompt and image here
        return ResponseEntity.ok(openAIresponse);
    }
}
