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
	
	private void updateInvoiceDate(InvoiceModel invoice) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDateTime now = LocalDateTime.now();
		invoice.setInvoiceDate(Date.valueOf(dtf.format(now)));
	}
	
	private void updateInvoiceTime(InvoiceModel invoice) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		invoice.setCreatedAt(dtf.format(now));
	}
	
//	public File generateInvoiceFor(InvoiceModel invoice) throws IOException {
//
//		File pdfFile = File.createTempFile("Invoice", ".pdf");
//
//		logger.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));
//
//		try (FileOutputStream pos = new FileOutputStream(pdfFile)) {
//			// Load invoice JRXML template.
//			final JasperReport report = loadTemplate();
//
//			// Fill parameters map.
//			final Map<String, Object> parameters = parameters(invoice);
//
//			// Create an empty datasource.
//			final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
//					Collections.singletonList("Invoice"));
//
//			// Render the invoice as a PDF file.
//			JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);
//
//			// return file.
//			return pdfFile;
//		} catch (final Exception e) {
//			logger.error(String.format("An error occured during PDF creation: %s", e));
//			throw new RuntimeException(e);
//		}
//	}
//
//	// Fill template order params
//	private Map<String, Object> parameters(InvoiceModel invoice) {
//		final Map<String, Object> parameters = new HashMap<>();
//		parameters.put("logo", getClass().getResourceAsStream(logo_path));
//		parameters.put("invoice", invoice);
//		parameters.put("invoiceNo", invoice.getInvoiceNo());
//		parameters.put("billingFirstName", invoice.getAddress().getBillingFirstName());
//		parameters.put("billingLastName", invoice.getAddress().getBillingLastName());
//		parameters.put("billingStreetName", invoice.getAddress().getBillingStreetName());
//		parameters.put("billingTown", invoice.getAddress().getBillingTown());
//		parameters.put("billingCountry", invoice.getAddress().getBillingCountry());
//		parameters.put("billingPostalCode", invoice.getAddress().getBillingPostalCode());
//		parameters.put("shippingFirstName", invoice.getAddress().getShippingFirstName());
//		parameters.put("shippingLastName", invoice.getAddress().getShippingLastName());
//		parameters.put("shippingStreetName", invoice.getAddress().getShippingStreetName());
//		parameters.put("shippingTown", invoice.getAddress().getShippingTown());
//		parameters.put("shippingCountry", invoice.getAddress().getShippingCountry());
//		parameters.put("shippingPostalCode", invoice.getAddress().getShippingPostalCode());
//		parameters.put("entriesSize", invoice.getAddress().getEntriesSize());
//		parameters.put("tax", invoice.getAddress().getSalesTax());
//		parameters.put("invoiceDate", invoice.getInvoiceDate());
//		parameters.put("dueDate", invoice.getDueDate());
//		parameters.put("dueAmount", invoice.getDueAmount());
//		parameters.put("withDue",
//				invoice.getDueAmount() != null
//						? invoice.getDueAmount() + invoice.getAddress().getTotalPrice()
//								+ invoice.getAddress().getGrandTotal()
//						: invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal());
//		parameters.put("grandTotal", invoice.getAddress().getGrandTotal());
//		parameters.put("finalPrice", (invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal()));
//		long diff = getDifferenceDays(invoice.getInvoiceDate(), invoice.getDueDate());
//		parameters.put("notes",
//				" 1. Remit payment within " + diff + " days of date of invoice. \n"
//						+ " 2. Please make payment through Paypal.\n"
//						+ " 3. All deposits and payments are subject to terms and\n"
//						+ "     conditions in the client service agreement.");
//		return parameters;
//	}
//
//	public static long getDifferenceDays(Date d1, Date d2) {
//		long diff = d2.getTime() - d1.getTime();
//		return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
//	}
//
//	// Load invoice JRXML template
//	private JasperReport loadTemplate() throws JRException {
//
//		logger.info(String.format("Invoice template path : %s", invoice_template));
//
//		final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
//		final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);
//
//		return JasperCompileManager.compileReport(jasperDesign);
//	}
//
//	public InvoiceModel getInvoiceById(String id) {
//		return invoiceDao.getInvoiceById(id);
//	}
//
//	public InvoiceModel updateInvoice(InvoiceModel invoiceDetails) {
//		setOrderEntryDetails(invoiceDetails.getAddress().getEntries(), invoiceDetails.getInvoiceNo());
//		setAddressDetails(invoiceDetails.getAddress(), invoiceDetails.getInvoiceNo());
//		invoiceDao.save(invoiceDetails);
//		return invoiceDetails;
//	}
//
//	public void updateInvoicePayment(Double paid, String invoiceNo) {
//		InvoiceModel invoice = getInvoice(invoiceNo);
//		Double total = invoice.getDueAmount() + invoice.getAddress().getTotalPrice()
//				+ invoice.getAddress().getGrandTotal();
//		if (total == paid) {
//			invoice.setPaidAmount(paid);
//			invoice.setStatus("PAID");
//			invoice.setDueAmount(total - paid);
//		} else if ((total > paid)) {
//			invoice.setPaidAmount(paid);
//			invoice.setStatus("PARTLY PAID");
//			invoice.setDueAmount(total - paid);
//		}
//		updateInvoice(invoice);
//	}

}
