package com.dealalert.webapp.utils;

import com.dealalert.webapp.models.Alert;
import com.dealalert.webapp.repository.AlertRepository;
import com.dealalert.webapp.services.AlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@EnableScheduling
public class SchedulingTasks {

    private final AlertService alertService;

    private final long SEC = 1000;
    private final long INTERVAL = SEC * 5;

    @Autowired
    public SchedulingTasks(AlertService alertService) {
        this.alertService = alertService;

    }

    @Scheduled(fixedDelay = INTERVAL)
    public void reportPrice() {
        if(alertService.existsByStatus(true)) {
            List<Alert> alerts = alertService.getAlertsByStatus(true);
            for (Alert temp : alerts) {
                System.out.println(temp.getAlertPrice() + " " + temp.getItemId() + " " + temp.getUsername());
                alertService.initMonitoringOfPrice(temp.getAlertPrice(), temp.getUsername(), temp.getEmail(), temp.getItemId());
            }
       }
    }

    @Scheduled(fixedDelay = INTERVAL)
    public void report(){

    }
}



