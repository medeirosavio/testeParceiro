package org.example.service;

import org.example.dto.NotificationRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {

    private final RestTemplate restTemplate;
    private final String notifyUrl = "https://util.devi.tools/api/v1/notify";

    @Autowired
    public NotificationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean sendNotification(NotificationRequestDTO notificationRequest) {
        try {
            HttpEntity<NotificationRequestDTO> request = new HttpEntity<>(notificationRequest);

            restTemplate.put(notifyUrl, request);

            return true;
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificação: " + e.getMessage());
            return false;
        }
    }
}
