package com.ximedes.bitcoin.miniproject;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Log4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    @Autowired
    private StoreService storeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

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
