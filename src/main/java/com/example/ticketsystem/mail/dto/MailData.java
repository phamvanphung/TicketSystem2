package com.example.ticketsystem.mail.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class MailData {
    private String to;
    private String subject;
    private String templateName;
    private Map<String, Object> pros;
    private String from;
}
