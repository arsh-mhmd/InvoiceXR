package invoice.xr.service;
import invoice.xr.dao.RegisterDao;
import invoice.xr.dao.TimerDao;
import invoice.xr.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Address;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Jue Wang
 */
@Service
public class SendEmailService {
    @Autowired InvoiceService invoiceService;
    @Autowired
    RegisterDao registerDao;
    @Autowired MailService mailService;
    @Autowired
    TimerDao timerDao;

	public String getInvoiceData(InvoiceModel invoiceModel) {
		AddressModel addressModel = invoiceModel.getAddress();
		String header = "Hi " + addressModel.getShippingFirstName() + " " + addressModel.getShippingLastName()
				+ "," + "\n\nThis is an invoice from InvoiceXR Inc.\n\n";
		String billing = "Bill to         :  " + addressModel.getBillingFirstName() + addressModel.getBillingLastName()
				+ " \nAddress     :  " + addressModel.getBillingStreetName()
				+ " " + addressModel.getBillingTown() + " " + addressModel.getBillingCountry() + " \nPost Code :  "
				+ addressModel.getBillingPostalCode() + "\n\n";
		String shipping = "Ship to       : " + addressModel.getShippingFirstName() + addressModel.getShippingLastName()
				+ " \nAddress    :  "
				+ addressModel.getShippingStreetName() + " " + addressModel.getShippingTown() + " "
				+ addressModel.getShippingCountry() + " \nPost Code : " + addressModel.getShippingPostalCode() + "\n\n";
		String date = "Invoice Date: " + invoiceModel.getInvoiceDate() + "    \nDue Date: " + invoiceModel.getDueDate()
				+ "    Remaining Due: " + invoiceModel.getDueAmount() + "\n";

		double withDue = invoiceModel.getDueAmount() != null
				? invoiceModel.getDueAmount() + invoiceModel.getAddress().getTotalPrice()
						+ invoiceModel.getAddress().getGrandTotal()
				: invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal();
		String money = "Sale Tax: " + addressModel.getSalesTax() + " Salex Tax Price: " + addressModel.getGrandTotal()
				+ " Total Price: " + invoiceModel.getAddress().getTotalPrice() + " With Due: " + withDue;
		String end = "\n\nBest Wishes,\nInvoiceXr Inc.";
		return header + billing + shipping + date + money + end;
	}

	public void sendInvoiceNow(String number) throws IOException {
		final InvoiceModel invoice = invoiceService.getInvoice(number);
		String clientId = invoice.getClientId();
		final ClientUser clientUser = registerDao.findClientById(clientId);
		String Email = clientUser.getEmail();
		String content = this.getInvoiceData(invoice);
        final File invoicePdf = invoiceService.generateInvoiceFor(invoice);
//		mailService.sendSimpleMail(Email, "Please check your invoice. This invoice is from InvoiceXr inc.", content);
		mailService.sendAttachmentsMail(Email, "Please check your invoice. This invoice is from InvoiceXr inc.", content, invoicePdf, number);
	}

    public TimerModel setTimerConfig(TimerModel timerModel){
        if (timerModel.getType()==0){
            timerModel.setDueDay(30);
        }
        timerModel.setSent(0);
        timerDao.save(timerModel);
        return timerModel;
    }

    public void sendMonthlyEmail() throws ParseException, IOException {
        List<TimerModel> timerTasks = timerDao.findByType(0);
        for(int i =0; i<timerTasks.size();i++){
            TimerModel timerTask = timerTasks.get(i);

            Date today = new Date();
            if(timerTask.getLastSentDay()==null || (today.getTime()-timerTask.getLastSentDay().getTime())/(1000 * 60 * 60 * 24) > 30){
                // send email
                sendInvoiceNow(timerTask.getInvoiceId());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String dateString = format.format(today);
                Date date = format.parse(dateString);
                timerTask.setLastSentDay((java.sql.Date) date);
                timerDao.save(timerTask);
            }
        }
    }

    public void sendDueEmail() throws ParseException, IOException {
        List<TimerModel> timerTasks = timerDao.findByType(1);
        for(int i =0; i<timerTasks.size();i++){
            TimerModel timerTask = timerTasks.get(i);
            InvoiceModel invoiceModel = invoiceService.getInvoice(timerTask.getInvoiceId());
            Date today = new Date();
            if (timerTask.getSent()==0){
                if ((invoiceModel.getDueDate().getTime()-today.getTime())/(1000 * 60 * 60 * 24)<timerTask.getDueDay()){
                    sendInvoiceNow(timerTask.getInvoiceId());
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = format.format(today);
                    Date date = format.parse(dateString);

                    timerTask.setLastSentDay((java.sql.Date) date);
                    timerTask.setSent(1);
                    timerDao.save(timerTask);
                }
            }

        }

    }
}
