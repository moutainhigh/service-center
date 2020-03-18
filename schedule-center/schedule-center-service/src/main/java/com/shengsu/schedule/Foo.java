package com.shengsu.schedule;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class Foo {

    @Scheduled(cron="${schedule.cron}")
    void task(){
        System.out.println("helloooooooo");
    }
}
