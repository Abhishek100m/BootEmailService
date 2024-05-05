package com.email.bootEmailService.bo;

import lombok.Data;

import javax.mail.Multipart;

@Data
public class EmailDetails {
    private String to;
    private String from;
    private String cc;
    private String bcc;
    private Multipart multipart;
    private String subject;
    private String body;
}
