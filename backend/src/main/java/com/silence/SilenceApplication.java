package com.silence;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Entry Point for the SILENCE Backend service.
 * Initializes the Spring context and starts the embedded web server.
 */
@SpringBootApplication
public class SilenceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilenceApplication.class, args);
    }

}
