package com.os.yerinial.scheduler;

import com.os.yerinial.service.EventService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class Scheduler {

    private final EventService eventService;

    public Scheduler(EventService eventService) {
        this.eventService = eventService;
    }

    // Her saatin 5.dakikası tetiklenerek eventDate süresi geçen etkinlikleri Status değerini günceller.
    @Scheduled(cron = "0 5 * * * *")
    public void runAtEveryHourFifthMinute() {
        eventService.expiredEventDate();
    }

    // eventDate süresi 3 gün geçmiş olanları dbden hard deleted yapar.
    @Scheduled(cron = "0 0 0 */3 * *")
    public void runEveryThreeDaysAtMidnight() {
        eventService.expiredEventDateDays(3);
    }
}
