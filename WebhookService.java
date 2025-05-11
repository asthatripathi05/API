/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package webhooksolver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import webhooksolver.dto.InitialRequest;
import webhooksolver.dto.InitialResponse;
import webhooksolver.dto.SolutionSubmission;
import webhooksolver.util.JwtUtil;

public class WebhookService {


    private static final String INITIAL_URL = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
    private static final String NAME = "John Doe";
    private static final String REG_NO = "REG12347";
    private static final String EMAIL = "john@example.com";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DatabaseQuestionService databaseQuestionService;

    @Autowired
    private JwtUtil jwtUtil;

    public void executeWebhookFlow() {
        try {
            // Step 1: Send initial POST request
            InitialResponse response = sendInitialRequest();
            
            if (response == null || response.getWebhook() == null || response.getAccessToken() == null) {
                System.err.println("Invalid response received from initial request");
                return;
            }

            // Step 2: Solve the SQL problem based on regNo
            String sqlSolution = databaseQuestionService.solveQuestion(REG_NO);
            
            // Step 3: Submit the solution to the webhook URL
            submitSolution(response.getWebhook(), response.getAccessToken(), sqlSolution);
            
        } catch (Exception e) {
            System.err.println("Error during webhook flow execution: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private InitialResponse sendInitialRequest() {
        InitialRequest request = new InitialRequest(NAME, REG_NO, EMAIL);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        HttpEntity<InitialRequest> entity = new HttpEntity<>(request, headers);
        
        ResponseEntity<InitialResponse> response = restTemplate.exchange(
            INITIAL_URL,
            HttpMethod.POST,
            entity,
            InitialResponse.class
        );
        
        return response.getBody();
    }

    private void submitSolution(String webhookUrl, String accessToken, String sqlSolution) {
        SolutionSubmission submission = new SolutionSubmission(sqlSolution);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", jwtUtil.formatToken(accessToken));
        
        HttpEntity<SolutionSubmission> entity = new HttpEntity<>(submission, headers);
        
        ResponseEntity<String> response = restTemplate.exchange(
            webhookUrl,
            HttpMethod.POST,
            entity,
            String.class
        );
        
        System.out.println("Solution submitted successfully. Response: " + response.getBody());
    }
} 

