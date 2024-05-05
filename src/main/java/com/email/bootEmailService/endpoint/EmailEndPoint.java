package com.email.bootEmailService.endpoint;

import com.email.bootEmailService.bo.EmailDetails;
import com.email.bootEmailService.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class EmailEndPoint {
    @Autowired
    private EmailService emailService;

    @GetMapping("/testing")
    public String hello(){
        return "Hello test api Hi prakash !";
    }
    @PostMapping("/sendMail")
    public ResponseEntity<String> sendEmail(@RequestBody EmailDetails emailDetails){
        try {
            boolean result = emailService.sendEmail(emailDetails);
            if(result)
               return new ResponseEntity<>("email send", HttpStatus.OK);
            else
                return new ResponseEntity<>("something wents wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }catch (Exception e){
            return new ResponseEntity<>("something wents wrong, e:"+ e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
