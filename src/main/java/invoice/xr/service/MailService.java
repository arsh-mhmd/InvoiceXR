package invoice.xr.service;

/**
 * @author Jue Wang
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    public void sendHtmlMail(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content, String filePath);
}