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
	@Autowired
	InvoiceService invoiceService;
	@Autowired
	RegisterDao registerDao;
	@Autowired
	MailService mailService;
	@Autowired
	TimerDao timerDao;
	@Autowired
	QuoteService quoteService;

	public String getInvoiceData(InvoiceModel invoiceModel) {
		AddressModel addressModel = invoiceModel.getAddress();
		String header = "<html><body>Hi " + addressModel.getShippingFirstName() + " "
				+ addressModel.getShippingLastName() + "," + "<p>This is a billing notice that your invoice no. <b>"
				+ invoiceModel.getInvoiceNo() + "</b> which was generated on <b>" + invoiceModel.getInvoiceDate()
				+ "</b> from InvoiceXR Inc.</p>";
		String date = "<p style=\"background-color:powderblue;width:15%;\"><b>Invoice Date: " + invoiceModel.getInvoiceDate()
				+ "    </b></p><p style=\"background-color:tomato;width:15%;\"><b>Due Date: " + invoiceModel.getDueDate() + "    </b></p>";
		String table = "<table border=\"3\" style=\"width:40%;border-collapse:collapse;\">\r\n" + "  <tr>\r\n"
				+ "    <th>Billing Details</th>\r\n" + "    <td><p>" + addressModel.getBillingFirstName() + " "
				+ addressModel.getBillingLastName() + " <br>" + addressModel.getBillingStreetName() + "<br>"
				+ addressModel.getBillingTown() + ", " + addressModel.getShippingCountry() + " <br>Post Code : "
				+ addressModel.getBillingPostalCode() + "</p></td> \r\n" + "  </tr>\r\n" + "  <tr>\r\n"
				+ "    <th>Shipping Details</th>\r\n" + "    <td><p>" + addressModel.getShippingFirstName() + " "
				+ addressModel.getShippingLastName() + " <br>" + addressModel.getShippingStreetName() + "<br>"
				+ addressModel.getShippingTown() + ", " + addressModel.getShippingCountry() + " <br>Post Code : "
				+ addressModel.getShippingPostalCode() + "</p></td> \r\n" + "  </tr>\r\n" + "</table>";
		String attach = "<p>Please find your in invoice in the attachment.</hp>";
		double withDue = invoiceModel.getDueAmount() != null
				? invoiceModel.getDueAmount() + invoiceModel.getAddress().getTotalPrice()
						+ invoiceModel.getAddress().getGrandTotal()
				: invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal();
		String beforeEnd = "<h4><b>Best Wishes,</h4><h4>InvoiceXr Inc.</b></h4>";
		String end = "<h4>You can make payment by clicking on this <a href=\"http://localhost:8081/InvoiceXR/pay?"
				+ "total=" + withDue + "&invoiceNo=" + invoiceModel.getInvoiceNo() + "\">link</a> !"
				+ "</h4></body></html>";
		return header + table + date + end + attach + beforeEnd;
	}

	public void sendInvoiceNow(String number) throws IOException {
		final InvoiceModel invoice = invoiceService.getInvoice(number);
		String clientId = invoice.getClientId();
		final ClientUser clientUser = registerDao.findClientById(clientId);
		String Email = clientUser.getEmail();
		String content = this.getInvoiceData(invoice);
		final File invoicePdf = invoiceService.generateInvoiceFor(invoice);
//		mailService.sendSimpleMail(Email, "Please check your invoice. This invoice is from InvoiceXr inc.", content);
		mailService.sendAttachmentsMail(Email, "Please check your invoice. This invoice is from InvoiceXr inc.",
				content, invoicePdf, number);
	}
	public void sendRecipe(InvoiceModel invoiceModel,PaymentRecordModel paymentRecordModel){
		final ClientUser clientUser = registerDao.findClientById(paymentRecordModel.getClientId());

		String Email = clientUser.getEmail();
		String content = this.getRecipeData(paymentRecordModel,invoiceModel);
		mailService.sendHtmlMail(Email,"Please check your recipe. This recipe is from InvoiceXr inc.",content);
	}

	public String getRecipeData(PaymentRecordModel paymentRecordModel,InvoiceModel invoiceModel){
		AddressModel addressModel = invoiceModel.getAddress();
		String header = "<html><body>Hi " + addressModel.getShippingFirstName() + " "
				+ addressModel.getShippingLastName() + "," + "<p>This is a recipe of your invoice no. <b>"
				+ invoiceModel.getInvoiceNo() + "</b> which was generated on <b>" + invoiceModel.getInvoiceDate()
				+ "</b> from InvoiceXR Inc.</p>";
		String date = "<p style=\"background-color:powderblue;width:15%;\"><b>Invoice Date: " + invoiceModel.getInvoiceDate()
				+ "    </b></p><p style=\"background-color:tomato;width:15%;\"><b>Due Date: " + invoiceModel.getDueDate() + "    </b></p>";

		String table = "<table border=\"3\" style=\"width:40%;border-collapse:collapse;\">\r\n" + "  <tr>\r\n"
				+ "    <th>Billing Details</th>\r\n" + "    <td><p>" + addressModel.getBillingFirstName() + " "
				+ addressModel.getBillingLastName() + " <br>" + addressModel.getBillingStreetName() + "<br>"
				+ addressModel.getBillingTown() + ", " + addressModel.getShippingCountry() + " <br>Post Code : "
				+ addressModel.getBillingPostalCode() + "</p></td> \r\n" + "  </tr>\r\n" + "  <tr>\r\n"
				+ "    <th>Shipping Details</th>\r\n" + "    <td><p>" + addressModel.getShippingFirstName() + " "
				+ addressModel.getShippingLastName() + " <br>" + addressModel.getShippingStreetName() + "<br>"
				+ addressModel.getShippingTown() + ", " + addressModel.getShippingCountry() + " <br>Post Code : "
				+ addressModel.getShippingPostalCode() + "</p></td> \r\n" + "  </tr>\r\n" + "</table>";
		String recipe = "<h4>You have paid "+paymentRecordModel.getPaid()+" GBP on "+paymentRecordModel.getCreatedDate()+"</h4>";
		String thanks = invoiceModel.getDueAmount()!=0 ? "<h4>Thanks for you payment, You have "+invoiceModel.getDueAmount()+" GBP left to pay</h4>" :
				"<h4>Thanks for you payment, you have paid all the money</h4>";
		String end = "<h4><b>Best Wishes,</h4><h4>InvoiceXr Inc.</b></h4>";
		return  header+table+date+recipe+thanks+end;
	}
	public TimerModel setTimerConfig(TimerModel timerModel) {
		if (timerModel.getType() == 0) {
			timerModel.setDueDay(30);
		}
		timerModel.setSent(0);
		timerDao.save(timerModel);
		return timerModel;
	}

	public void sendMonthlyEmail() throws ParseException, IOException {
		List<TimerModel> timerTasks = timerDao.findByType(0);
		for (int i = 0; i < timerTasks.size(); i++) {
			TimerModel timerTask = timerTasks.get(i);

			Date today = new Date();
			if (timerTask.getLastSentDay() == null
					|| (today.getTime() - timerTask.getLastSentDay().getTime()) / (1000 * 60 * 60 * 24) > 30) {
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
		for (int i = 0; i < timerTasks.size(); i++) {
			TimerModel timerTask = timerTasks.get(i);
			InvoiceModel invoiceModel = invoiceService.getInvoice(timerTask.getInvoiceId());
			Date today = new Date();
			if (timerTask.getSent() == 0) {
				if ((invoiceModel.getDueDate().getTime() - today.getTime()) / (1000 * 60 * 60 * 24) < timerTask
						.getDueDay()) {
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

	public void sendQuoteNow(String quoteNo) {
		final QuoteModel quote = quoteService.getQuote(quoteNo);
		String clientId = quote.getClientId();
		final ClientUser clientUser = registerDao.findClientById(clientId);
		String Email = clientUser.getEmail();
		String content = this.getQuoteData(quote);
		mailService.sendHtmlMail(Email, "Please check your quote. This quote is from InvoiceXr inc.", content);
	}
	
	public String getQuoteData(QuoteModel quote) {
		String header = "<html><body>Hi " + quote.getClientName() + "," + "<p>This is a quote that your quote no. <b>"
				+ quote.getQuoteNo() + "</b> which was generated on <b>" + quote.getQuoteDate()
				+ "</b> from InvoiceXR Inc.</p>";
		
		String beforeEnd = "<h4><b>Best Wishes,</h4><h4>InvoiceXr Inc.</b></h4>";
		String end = "<p>You can verify quote by clicking on this <a href=\"http://localhost:8080/clientQuote?"
				+ "quoteNo=" + quote.getQuoteNo() + "\">link</a> !"
				+ "</p></body></html>";
		return header + end + beforeEnd;
	}
}
