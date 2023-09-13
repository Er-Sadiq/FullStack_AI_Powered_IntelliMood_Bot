package Chatbot.IM_AI.service;

import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.History;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.utils.IOUtils;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
public class Chatbot {

    private static final boolean TRACE_MODE = false;
    private static final String botName = "Intellimood"; // Use the variable here
    private String inputResponse;

    public void setInputResponse(String inputResponse) {
        this.inputResponse = inputResponse;
    }

    public String getResponse() {
        try {
            String resourcesPath = getResourcesPath();
            System.out.println(resourcesPath);
            MagicBooleans.trace_mode = TRACE_MODE;
            Bot bot = new Bot(botName, resourcesPath); // Use botName variable
            Chat chatSession = new Chat(bot);
            bot.brain.nodeStats();

            String textLine = inputResponse;
            if ((textLine == null) || (textLine.length() < 1))
                textLine = MagicStrings.null_input;

            if (textLine.equals("q")) {
                System.exit(0);
            } else if (textLine.equals("wq")) {
                bot.writeQuit();
                System.exit(0);
            } else {
                String request = textLine;
                if (MagicBooleans.trace_mode)
                    System.out.println(
                            "STATE=" + request + ":THAT=" + ((History) chatSession.thatHistory.get(0)).get(0)
                                    + ":TOPIC=" + chatSession.predicates.get("topic"));
                String response = chatSession.multisentenceRespond(request);
                while (response.contains("&lt;"))
                    response = response.replace("&lt;", "<");
                while (response.contains("&gt;"))
                    response = response.replace("&gt;", ">");
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Error occurred: " + e.getMessage();
        }
        return "Unhandled input";
    }

    private static String getResourcesPath() {
        String path = System.getProperty("user.dir");
        String resourcesPath = path + File.separator + "src" + File.separator + "main" + File.separator + "resources";
        return resourcesPath;
    }
}
