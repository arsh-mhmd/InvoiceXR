package invoice.xr.controller;

import invoice.xr.service.MailService;
import invoice.xr.service.RegistrationService;
import invoice.xr.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Jue Wang
 */
@Controller
@RequestMapping("/")
public class SendMailController {

    @Autowired
    SendEmailService sendEmailService;

    @GetMapping("/sendInvoiceNow")
    public String SendInvoiceNow(String Number){

        sendEmailService.sendInvoiceNow(Number);
        return "Send Email Success!";
    }
}
