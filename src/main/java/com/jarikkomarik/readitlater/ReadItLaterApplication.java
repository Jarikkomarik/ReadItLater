package com.jarikkomarik.readitlater;

import com.jarikkomarik.readitlater.exception.MissingTokenException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ReadItLaterApplication implements CommandLineRunner {

    public static void main(String[] args) {
        if(System.getenv("bot.token") == null) throw new MissingTokenException();
        SpringApplication.run(ReadItLaterApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
