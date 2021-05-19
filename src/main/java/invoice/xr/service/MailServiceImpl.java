package invoice.xr.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.io.File;
/**
 * @author Jue Wang
 */
@Service
public class MailServiceImpl implements MailService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JavaMailSender mailSender;


    @Value("InvoiceXR Inc.<935730017@qq.com>")
    private String from;


    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(from);

        message.setTo(to);

        message.setSubject(subject);

        message.setText(content);

        mailSender.send(message);
    }


    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(message, true);

            messageHelper.setFrom(from);

            messageHelper.setTo(to);

            message.setSubject(subject);

            messageHelper.setText(content, true);

            mailSender.send(message);

            logger.info("mail send success");
        } catch (MessagingException e) {
            logger.error("mail send failed", e);
        }
    }

	@Override
    public void sendAttachmentsMail(String to, String subject, String content, File file, String fileName) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
        	
        	message.setFrom(from);
        	message.setRecipients(Message.RecipientType.TO, to);
        	message.setSubject(subject);
        	
        	Multipart multipart = new MimeMultipart();
        	BodyPart attachmentBodyPart = new MimeBodyPart();
        	DataSource source = new FileDataSource(file);
        	attachmentBodyPart.setDataHandler(new DataHandler(source));
        	attachmentBodyPart.setFileName(fileName+".pdf");
        	multipart.addBodyPart(attachmentBodyPart);
        	
        	BodyPart htmlBodyPart = new MimeBodyPart();
        	htmlBodyPart.setContent(content, "text/html");
        	multipart.addBodyPart(htmlBodyPart);
        	message.setContent(multipart);
//            MimeMessageHelper helper = new MimeMessageHelper(message, true);
//            helper.setFrom(from);
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(content, true);
//            helper.addAttachment(fileName+".pdf", file);
            mailSender.send(message);
            //log
            logger.info("mail send success");
        } catch (MessagingException e) {
            logger.error("mail send failed", e);
        }
    }
}
