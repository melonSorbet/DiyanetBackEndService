package org.prayertime;

import org.prayertime.config.AppConfig;
import org.prayertime.controller.DiyanetApiController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication

public class Application {

    public final DiyanetApiController diyanetApiController;

    public Application(DiyanetApiController diyanetApiController){
        this.diyanetApiController = diyanetApiController;
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        AppConfig appConfig = context.getBean(AppConfig.class);
    }
}