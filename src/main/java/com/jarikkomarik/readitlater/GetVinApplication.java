package com.jarikkomarik.readitlater;

import com.jarikkomarik.readitlater.exception.MissingTokenException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GetVinApplication {

    public static void main(String[] args) {
        if(System.getenv("bot.token") == null) throw new MissingTokenException();
        SpringApplication.run(GetVinApplication.class, args);
    }

}
