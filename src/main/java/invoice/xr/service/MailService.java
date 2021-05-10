package invoice.xr.service;

import java.io.File;

/**
 * @author Jue Wang
 */
public interface MailService {

    void sendSimpleMail(String to, String subject, String content);

    public void sendHtmlMail(String to, String subject, String content);

    public void sendAttachmentsMail(String to, String subject, String content, File file, String fileName);


}