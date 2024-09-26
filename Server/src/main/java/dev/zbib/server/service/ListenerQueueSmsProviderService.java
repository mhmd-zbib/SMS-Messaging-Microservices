package dev.zbib.server.service;

import org.springframework.stereotype.Service;

@Service
public class ListenerQueueSmsProviderService extends AQueueSmsProviderService {
    protected ListenerQueueSmsProviderService() {
        super("sms-listener");
    }
}