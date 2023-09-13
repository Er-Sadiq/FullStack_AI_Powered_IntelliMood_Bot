package Chatbot.IM_AI.controller;
import Chatbot.IM_AI.service.Chatbot;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class MyController {

    private final Chatbot chatbot;

    public MyController(Chatbot chatbot) {
        this.chatbot = chatbot;
    }

    @PostMapping("/ask")
    public String sendRequest(@RequestParam String userInput) {
        chatbot.setInputResponse(userInput);
        return chatbot.getResponse();

    }
}
