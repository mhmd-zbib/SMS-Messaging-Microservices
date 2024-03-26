package dev.zbib.server.service;

import org.springframework.stereotype.Service;

@Service
public class ScheduledQueueSmsProviderService extends AQueueSmsProviderService {
    protected ScheduledQueueSmsProviderService() {
        super("sms-cron");
    }
}
