package invoice.xr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;

/**
 * @author Jue Wang
 */
@Configuration
@EnableScheduling
public class TimerTask {
    @Autowired SendEmailService sendEmailService;

    @Scheduled(cron = "0 0 1 * * ?")
    private void configureTasks() {
        System.err.println("Timer running time is: " + LocalDateTime.now());
    }
    @Scheduled(cron = "0 0 1 * * ?")
    private void sendMonthlyEmail() throws ParseException, IOException {
        sendEmailService.sendMonthlyEmail();
    }

    @Scheduled(cron = "0 0 1 * * ?")
    private void sendDueEmail() throws ParseException, IOException {
        sendEmailService.sendDueEmail();
    }

}
