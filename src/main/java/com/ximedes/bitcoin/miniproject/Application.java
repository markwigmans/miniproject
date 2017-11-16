package com.ximedes.bitcoin.miniproject;

import com.ximedes.bitcoin.miniproject.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start point of the application, which is run from the command line.
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final StoreService storeService;
    private final boolean hashValue;

    @Autowired
    public Application(StoreService storeService, @Value("${value.hashed:true}") boolean hashValue) {
        this.storeService = storeService;
        this.hashValue = hashValue;
    }

    /**
     * Start point of the application
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Run the application with the given command line arguments
     */
    @Override
    public void run(String... args) {
        try {
            if (args.length > 0) {
                storeService.store(args[0], hashValue);
            } else {
                System.err.println("Usage: java -jar bmp.jar <argument to store>");
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getLocalizedMessage());
            log.error("Exception:", e);
        }
    }
}
