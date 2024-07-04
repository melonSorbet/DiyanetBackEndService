package org.prayertime.controller;

import org.prayertime.model.DailyContentDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {
    @GetMapping("/")
    public String getHello() {
        return "Stinking time";
    }

    @GetMapping("/daily")
    public DailyContentDto getDaily() {
        return new DailyContentDto(12, 24082004, "fatiha", "", "the prophet has eaten chicken", "bukhari?", "ey bizi niymetleriyle perveeden sultanimiz", "");
    }
}
