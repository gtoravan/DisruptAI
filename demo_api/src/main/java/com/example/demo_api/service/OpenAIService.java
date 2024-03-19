package main.java.com.example.demo_api.service;

import jakarta.annotation.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OpenAIService {

    //field level dependency injection
//    @Autowired
//    public RestTemplate restTemplate;

    //constructor level injection
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public OpenAIService(RestTemplate restTemplate){
//        this.restTemplate = restTemplate;
//    }

    private final RestTemplate restTemplate;

    public OpenAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    public String callOpenAI(String prompt, String imageBase64) {
//        // Your logic to call OpenAI here
//        // For example:
//        // restTemplate.postForObject(apiUrl, requestBody, ResponseClass.class);
//        return "OPENAI";
//    }
    public ResponseEntity<byte[]> callOpenAI(String prompt, String imageBase64) throws InterruptedException, IOException {
        String gptPrompt = chatGPT(prompt);

        sendPromptMidjourney(gptPrompt);

        Thread.sleep(30000); //30 seconds
        receivePromptMidjourney();
        String filePath = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/images/" + discoverFile();
        Path path = Paths.get(filePath);
        byte[] imageBytes = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageBytes);
    }


    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "openaikey";
        String model = "gpt-3.5-turbo";

//        String systemPrompt = "";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            String body = "{\"model\": \"" + model +
                    "\", \"messages\": " +
                    "[" +
                    "{\"role\": \"system\", \"content\": \"You are advertisement agency worker. Craft a prompt for midjourney to generate an image for advertisement of mentioned product based on prompt. The prompt will be a user demographic and information about the product. The prompt/response you craft will be fed into an image generation model to generate a picture that will be used to advertise to target demographic.\"}," +
                    "{\"role\": \"user\", \"content\": \"" + prompt + "\"}" +
                    "]" +
                    "}";

            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            return extractMessageFromJSONResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        return response.substring(start, end);

    }

    //MIDJOURNEY CALL
    public String sendPromptMidjourney(String gptPrompt) {
        try {
            // Command to run Python file with parameters
            String pythonCommand = "python3";
            String pythonScriptPath = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/sender.py";
            String paramsFilePath = "--params";
            String paramsFilePathValue = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/sender_params.json";
            String promptParam = "--prompt";
            String promptValue = "/imagine " + gptPrompt;

            // Build the command
            ProcessBuilder pb = new ProcessBuilder(pythonCommand, pythonScriptPath, paramsFilePath, paramsFilePathValue, promptParam, promptValue);
            pb.redirectErrorStream(true);

            // Start the process
            Process process = pb.start();

            // Wait for the process to finish
            process.waitFor();

            // Get the output of the process
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Close the reader
            reader.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "midjourney CALLED";
    }

    public void receivePromptMidjourney() {
        try {
            // Command to run Python file with parameters
            String pythonCommand = "python3";
            String pythonScriptPath = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/receiver.py";
            String paramsFilePath = "--params";
            String paramsFilePathValue = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/sender_params.json";
            String localPathParam = "--local_path";
            String localPathParamValue = "/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/images";

            // Build the command
            ProcessBuilder pb = new ProcessBuilder(pythonCommand, pythonScriptPath, paramsFilePath, paramsFilePathValue, localPathParam, localPathParamValue);
            pb.redirectErrorStream(true);

            // Start the process
            Process process = pb.start();


            // Close the reader

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String discoverFile() {
        String returnVal = null;
        try {
            returnVal = "";
            // Create a WatchService
            WatchService watchService = FileSystems.getDefault().newWatchService();

            // Register the directory to watch with the WatchService
            Path directory = Paths.get("/Users/gauravtoravane/Documents/GitHub/DisruptAI/GEORGEIAM-FORK-Midjourney_api/images");
            directory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            // Infinite loop to wait for events
                WatchKey key = watchService.take(); // Wait for a key to be signalled
                for (WatchEvent<?> event : key.pollEvents()) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) {
                        // A new file was created
                        Path newPath = (Path) event.context();
                        // Check if it's an image file
                        if (isImageFile(newPath.toString())) {
                            // Process the image file
                            System.out.println("New image file added: " + newPath);
                            returnVal = newPath.toString();
                            break;
                        }
                    }
                }
                key.reset(); // Reset the key
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    private static boolean isImageFile(String fileName) {
        String[] imageExtensions = {".jpg", ".jpeg", ".png", ".gif"};
        for (String extension : imageExtensions) {
            if (fileName.toLowerCase().endsWith(extension)) {
                return true;
            }
        }
        return false;
    }

}
