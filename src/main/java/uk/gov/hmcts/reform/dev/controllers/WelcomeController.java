package uk.gov.hmcts.reform.dev.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Welcome controller for the root endpoint.
 */
@RestController
public class WelcomeController {
    
    /**
     * Welcome endpoint.
     *
     * @return Welcome message
     */
    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String welcome() {
        return "Welcome to the Task Management API";
    }
}