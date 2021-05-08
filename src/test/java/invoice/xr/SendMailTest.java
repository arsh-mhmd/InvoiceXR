package invoice.xr;


import invoice.xr.service.MailService;
import invoice.xr.service.SendEmailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
public class SendMailTest {
    @Autowired
    private MailService mailService;

    @Autowired
    private SendEmailService sendEmailService;
    /**
     * test normal email
     */
    @Test
    public void sendmail () {
        //mailService.sendSimpleMail("juewolf@bupt.edu.cn", "subject：hello, this is normal email", "content：the first email");
        sendEmailService.sendInvoiceNow("IN-20210412213814");
    }

    /**
     * test file email
     */
//    @Test
//    public void sendmailFiles () {
//
//        mailService.sendAttachmentsMail("juewolf@bupt.edu.cn", "subject：hello, this is files email", "content：the first files email","src/test/java/invoice/xr/test.docx");
//    }
}