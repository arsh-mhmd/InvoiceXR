package invoice.xr.controller;


import invoice.xr.model.TimerModel;
import invoice.xr.service.MailService;
import invoice.xr.service.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/addNewTimer")
    public ResponseEntity<TimerModel> addNewTimer(@RequestBody TimerModel body){
        try {
            TimerModel timerModel = sendEmailService.setTimerConfig(body);
            return new ResponseEntity<>(timerModel, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
