package dev.maxuz.kwh.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final int SLEEP_TIME = 3;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        log.warn("Bad credentials");
        sleep();
    }

    private void sleep() {
        try {
            TimeUnit.SECONDS.sleep(SLEEP_TIME);
        } catch (InterruptedException e) {
            log.error("Sleep error", e);
        }
    }
}