package com.bpx.style_sphere_backend.services.interfaces;

import com.bpx.style_sphere_backend.models.dtos.OrderOutputDTO;
import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmail(String recipient, String subject, String status, OrderOutputDTO order) throws MessagingException;
}
