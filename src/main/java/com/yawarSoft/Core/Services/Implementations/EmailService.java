package com.yawarSoft.Core.Services.Implementations;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendCredentialsEmail(String to, String username, String rawPassword) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Credenciales de acceso");
        message.setText("Hola,\n\nTu cuenta ha sido creada.\n\nUsuario: " + username + "\nContraseña: " + rawPassword + "\n\nPor favor, cambia tu contraseña después de ingresar.");

        mailSender.send(message);
    }
}
