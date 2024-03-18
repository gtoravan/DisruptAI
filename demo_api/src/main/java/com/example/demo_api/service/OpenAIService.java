package main.java.com.example.demo_api.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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
    public String callOpenAI(String prompt, String imageBase64) {
        // Construct the request body
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("prompt", prompt);
//        requestBody.put("image", imageBase64);
//
//
//
//        // Set headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.set("Authorization", "Bearer YOUR_API_KEY_HERE");
//
//        // Create the HTTP entity
//        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
//
//        // Make the API call
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(OPENAI_API_URL, requestEntity, String.class);
//
//        if (responseEntity.getStatusCode() == HttpStatus.OK) {
//            return responseEntity.getBody();
//        } else {
//            // Handle error response
//            return "Error occurred: " + responseEntity.getStatusCode().toString();
//        }

        return chatGPT(prompt);
    }

    public static String chatGPT(String prompt) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "OPENAIKEY";
        String model = "gpt-3.5-turbo";

        String systemPrompt = "You are advertisement agency worker. Craft a prompt for midjourney based given prompt. The prompt will be a user demographic and information about the product. The prompt/response you craft will be fed into an image generation model to generate a picture that will be used to advertise to target democratic.";

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
                    "{\"role\": \"system\", \"content\": \"You are advertisement agency worker. Craft a prompt for midjourney based given prompt. The prompt will be a user demographic and information about the product. The prompt/response you craft will be fed into an image generation model to generate a picture that will be used to advertise to target democratic.\"}," +
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

}
