package com.esprit.jobfinder.jobs;
import com.esprit.jobfinder.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InactiveUserCleanupTask {

    @Autowired
    private IUserService userService;

    @Scheduled(fixedRate = 60000)
    public void cleanUpInactiveUsers() {
        userService.deleteInactiveUsers();
    }
}

