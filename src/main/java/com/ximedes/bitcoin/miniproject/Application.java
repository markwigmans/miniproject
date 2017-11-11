package com.ximedes.bitcoin.miniproject;

import com.ximedes.bitcoin.miniproject.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start point of the application, which is run from the command line.
 */
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StoreService storeService;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    /**
     * Run the application with the given command line arguments
     */
    @Override
    public void run(String... args) {
        try {
            if (args.length > 0) {
                storeService.store(args[0], true);
            } else {
                System.err.println("Usage: java -jar bmp.jar <argument to store>");
            }
        } catch (Exception e) {
            System.err.println("Exception: " + e.getLocalizedMessage());
            log.error("Exception:", e);
        }
    }
}
