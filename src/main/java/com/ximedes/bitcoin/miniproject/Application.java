package com.ximedes.bitcoin.miniproject;

import com.ximedes.bitcoin.miniproject.service.StoreService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Start point of the application, which is run from the command line.
 */
@Log4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StoreService storeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * Run the application with the given command line arguments
     */
    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            storeService.store(args[0]);
        } else {
            System.err.println("Usage: Exercise1Application <argument to store>");
        }
        System.exit(0);
    }
}
