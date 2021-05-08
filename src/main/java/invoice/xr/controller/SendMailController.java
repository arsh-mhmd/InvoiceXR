package invoice.xr.controller;

import invoice.xr.service.MailService;
import invoice.xr.service.RegistrationService;
import invoice.xr.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Jue Wang
 */
@Controller
public class SendMailController {

    @Autowired
    SendEmailService sendEmailService;

    @GetMapping("/sendInvoiceNow")
    public ResponseEntity<String> SendInvoiceNow(@RequestParam(value = "invoiceId") String invoiceId){
        System.out.println(invoiceId);
        sendEmailService.sendInvoiceNow(invoiceId);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
