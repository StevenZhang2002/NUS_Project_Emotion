package com.example.demo.Config;


import com.example.demo.DTO.PurchaseEvent;
import com.example.demo.Service.EmailService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PurchaseMessageListener {
    @Autowired
    EmailService emailService;


    @SneakyThrows
    @EventListener(PurchaseEvent.class)
    public void sendMsg(PurchaseEvent event) {
        int userId = event.getUserId();
        String targetEmail = event.getTargetEmail();
        emailService.sendSimpleMail(targetEmail,"your have purchased successfully","sb韩榕");
        log.info("send successfully");
    }
}
