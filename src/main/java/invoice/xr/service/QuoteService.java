package invoice.xr.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.jasperreports.JasperReportsUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import invoice.xr.model.AddressModel;
import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceModel;
import invoice.xr.model.OrderEntryModel;
import invoice.xr.model.QuoteEntryModel;
import invoice.xr.model.QuoteModel;
import lombok.NonNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import invoice.xr.dao.InvoiceDao;
import invoice.xr.dao.AddressDao;
import invoice.xr.dao.OrderEntryDao;
import invoice.xr.dao.QuoteDao;
import invoice.xr.dao.QuoteEntryDao;
import invoice.xr.dao.RegisterDao;
import invoice.xr.dao.ReportDao;

/**
 * @author Arshath Mohammed
 *
 */
@Service
public class QuoteService {

	@Autowired
	private QuoteDao quoteDao;
	@Autowired
	private QuoteEntryDao quoteEntryDao;
	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private RegisterDao registerDao;
	
	private static Logger logger = LogManager.getLogger(QuoteService.class);

	@Value("${invoice.logo.path}")
	private String logo_path;

	@Value("${invoice.template.path}")
	private String invoice_template;

	public void createNewQuote(QuoteModel quote) {
		quote.setQuoteNo(generateQuoteNo(quote.getQuoteDate()));
		quote.setClientId(quote.getClientId());
		quote.setShippingClientId(quote.getShippingClientId());
		quote.setId(generateQuoId());
		quote.setStatus("DRAFT");
		setQuoteEntryDetails(quote.getQuoteEntries());
		quoteDao.save(quote);
	}
	
	private String generateQuoId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}

	private void setQuoteEntryDetails(List<QuoteEntryModel> quoteEntries) {
		List<QuoteEntryModel> quoteEntryList = new ArrayList<>();
		for (QuoteEntryModel quoteEntry : quoteEntries) {
			QuoteEntryModel quoteEntryModel = new QuoteEntryModel();
			quoteEntryModel.setProductName(quoteEntry.getProductName());
			quoteEntryModel.setQuantity(quoteEntry.getQuantity());
			quoteEntryModel.setPrice(quoteEntry.getPrice());
			quoteEntryList.add(quoteEntryModel);
		}
		
	}

	private String generateQuoteNo(Date quoteDate) {
		String splitDate[] = quoteDate.toString().split("-");
		String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(Calendar.getInstance().getTime());
		String splitTime[] = timeStamp.split("_");
		String quoteNo = "QU-" + splitDate[0] + splitDate[1] + splitDate[2] + splitTime[1];
		return quoteNo;
	}
	
	private String generateInvoiceNo(Date invoiceDate) {
		String splitDate[] = invoiceDate.toString().split("-");
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
		String splitTime[] = timeStamp.split("_");
		String invoiceNo = "IN-" + splitDate[0] + splitDate[1] + splitDate[2] + splitTime[1];
		return invoiceNo;
	}

	private String generateInvoiceId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}

//	public void removeInvoiceById(String id) {
//		InvoiceModel invoiceModel = invoiceDao.getAddressId(id);
//		Integer addressId = invoiceModel.getAddress().getId();
//		orderEntryDao.deleteEntriesByOrderId(addressId);
//		invoiceDao.deleteInvoiceById(id);
//		addressDao.deleteAddressById(addressId);
//	}
//
	public List<QuoteModel> getAllQuotes() {
		return quoteDao.findAllQuotes();
	}

	public QuoteModel getQuote(String quoteNo) {
		return quoteDao.getQuoteByQuoteNo(quoteNo);
	}

	public void acceptQuote(String quoteNo) {
		QuoteModel quote = getQuote(quoteNo);
		quote.setStatus("ACCEPTED");
		quoteDao.save(quote);
	}
	
	public void declineQuote(String quoteNo) {
		QuoteModel quote = getQuote(quoteNo);
		quote.setStatus("DECLINED");
		quoteDao.save(quote);
	}

	public void createInvoiceFromQuote(QuoteModel quote) {
		InvoiceModel invoice = new InvoiceModel();
		invoice.setInvoiceNo(generateInvoiceNo(quote.getQuoteDate()));
		invoice.setId(generateInvoiceId());
		invoice.setClientId(quote.getClientId());
		invoice.setStatus("AWAITING PAYMENT");
		invoice.setPaidAmount(Double.parseDouble("0"));
		invoice.setInvoiceDate(quote.getQuoteDate());
		invoice.setDueDate(quote.getDueDate());
		updateInvoiceTime(invoice);
		setAddressDetails(invoice, quote);
		invoice.setDueAmount(invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal());
		invoiceDao.save(invoice);
	}
	
	private void setAddressDetails(InvoiceModel invoice, QuoteModel quote) {
		AddressModel addressDetails = new AddressModel();
		ClientUser client = registerDao.findClientById(quote.getClientId());
		addressDetails.setBillingFirstName(client.getFirstName());
		addressDetails.setBillingLastName(client.getLastName());
		addressDetails.setBillingStreetName(client.getStreetName());
		addressDetails.setBillingPostalCode(client.getPostalCode());
		addressDetails.setBillingTown(client.getTown());
		addressDetails.setBillingCountry(client.getCountry());
		
		ClientUser shippingClient = registerDao.findClientById(quote.getShippingClientId());
		addressDetails.setShippingFirstName(shippingClient.getFirstName());
		addressDetails.setShippingLastName(shippingClient.getLastName());
		addressDetails.setShippingStreetName(shippingClient.getStreetName());
		addressDetails.setShippingPostalCode(shippingClient.getPostalCode());
		addressDetails.setShippingTown(shippingClient.getTown());
		addressDetails.setShippingCountry(shippingClient.getCountry());
		addressDetails.setInvoiceNo(invoice.getInvoiceNo());
		addressDetails.setSalesTax(quote.getSalesTax());
		addressDetails.setEntries(setOrderEntryDetails(quote.getQuoteEntries(), invoice.getInvoiceNo()));
		invoice.setAddress(addressDetails);
	}
	
	private List<OrderEntryModel> setOrderEntryDetails(List<QuoteEntryModel> entries, String invoiceNo) {

		List<OrderEntryModel> orderEntryList = new ArrayList<>();
		for (QuoteEntryModel orderEntry : entries) {
			OrderEntryModel orderEntryModel = new OrderEntryModel();
			orderEntryModel.setProductName(orderEntry.getProductName());
			orderEntryModel.setProductName(orderEntry.getProductName());
			orderEntryModel.setQuantity(orderEntry.getQuantity());
			orderEntryModel.setPrice(orderEntry.getPrice());
			orderEntryList.add(orderEntryModel);
		}
		return orderEntryList;
	}
	
	private void updateInvoiceTime(InvoiceModel invoice) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		invoice.setCreatedAt(dtf.format(now));
	}

	public void removeQuote(String id) {
		quoteEntryDao.deleteEntriesByQuoteEntryId(id);
		quoteDao.deleteQuoteById(id);
		
	}

	public QuoteModel updateQuote(QuoteModel quote) {
		quoteDao.save(quote);
		return null;
	}

}
