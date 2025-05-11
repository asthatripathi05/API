/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package WebhookSolverApplication;
import org.springframework.boot.CommandLineRunner;
import webhooksolver.service.WebhookService;

public class WebhookSolverApplication implements CommandLineRunner {
  
    private WebhookService webhookService;

    public static void main(String[] args) {
        SpringApplication.run(WebhookSolverApplication.class, args);
    }

    public void run(String... args) throws Exception {
        webhookService.executeWebhookFlow();
    }  
}
