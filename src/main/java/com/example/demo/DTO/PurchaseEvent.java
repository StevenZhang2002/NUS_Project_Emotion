package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Data
@AllArgsConstructor
public class PurchaseEvent {
    public int userId;
    public String targetEmail;
    public String description;


}
