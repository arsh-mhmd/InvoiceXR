package invoice.xr.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import lombok.NonNull;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import invoice.xr.dao.InvoiceDao;
import invoice.xr.dao.AddressDao;
import invoice.xr.dao.OrderEntryDao;
import invoice.xr.dao.ReportDao;

/**
 * @author Arshath Mohammed
 *
 */
@Service
public class InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private OrderEntryDao orderEntryDao;
	@Autowired
	private ReportDao reportDao;

	private static Logger logger = LogManager.getLogger(InvoiceService.class);

    @Value("${invoice.logo.path}")
    private String logo_path;

    @Value("${invoice.template.path}")
    private String invoice_template;
    
	public void createNewInvoice(InvoiceModel invoiceDetails) {
		invoiceDetails.setInvoiceNo(generateInvoiceNo(invoiceDetails.getInvoiceDate()));
		invoiceDetails.setId(generateInvoiceId());
		invoiceDetails.setClientId(invoiceDetails.getClientId());
		setOrderEntryDetails(invoiceDetails.getAddress().getEntries(), invoiceDetails.getInvoiceNo());
		setAddressDetails(invoiceDetails.getAddress(), invoiceDetails.getInvoiceNo());
		invoiceDao.save(invoiceDetails);
		
	}

	private void setOrderEntryDetails(List<OrderEntryModel> entries, String invoiceNo) {
	
		List<OrderEntryModel> orderEntryList = new ArrayList<>();
	for (OrderEntryModel orderEntry : entries) {
		OrderEntryModel orderEntryModel = new OrderEntryModel();
		orderEntryModel.setProductName(orderEntry.getProductName());
		orderEntryModel.setQuantity(orderEntry.getQuantity());
		orderEntryModel.setPrice(orderEntry.getPrice());
		orderEntryModel.setInvoiceNo(invoiceNo);
		orderEntryList.add(orderEntryModel);
    }
}

	private void setAddressDetails(AddressModel address, String invoiceNo) {
		AddressModel addressDetails = new AddressModel();
		addressDetails.setBillingFirstName(address.getBillingFirstName());
		addressDetails.setBillingLastName(address.getBillingLastName());
		addressDetails.setBillingStreetName(address.getBillingStreetName());
		addressDetails.setBillingPostalCode(address.getBillingPostalCode());
		addressDetails.setBillingTown(address.getBillingTown());
		addressDetails.setBillingCountry(address.getBillingCountry());
		addressDetails.setShippingFirstName(address.getShippingFirstName());
		addressDetails.setShippingLastName(address.getShippingLastName());
		addressDetails.setShippingStreetName(address.getShippingStreetName());
		addressDetails.setShippingPostalCode(address.getShippingPostalCode());
		addressDetails.setShippingTown(address.getShippingTown());
		addressDetails.setShippingCountry(address.getShippingCountry());
		addressDetails.setInvoiceNo(invoiceNo);
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

	public void removeInvoiceById(String id) {
		InvoiceModel invoiceModel = invoiceDao.getAddressId(id);
		Integer addressId = invoiceModel.getAddress().getId();
		orderEntryDao.deleteEntriesByOrderId(addressId);
		invoiceDao.deleteInvoiceById(id);
		addressDao.deleteAddressById(addressId);
	}

	public List<InvoiceModel> getAllInvoices() {
		return invoiceDao.findAllInvoices();
	}
	
	public InvoiceModel getInvoice(String invoiceNo) {
		return invoiceDao.getInvoiceByInvoiceNo(invoiceNo);
	}
	
	public File generateInvoiceFor(InvoiceModel invoice) throws IOException {

        File pdfFile = File.createTempFile("Invoice", ".pdf");

        logger.info(String.format("Invoice pdf path : %s", pdfFile.getAbsolutePath()));

        try(FileOutputStream pos = new FileOutputStream(pdfFile))
        {
            // Load invoice JRXML template.
            final JasperReport report = loadTemplate();

            // Fill parameters map.
            final Map<String, Object> parameters = parameters(invoice);

            // Create an empty datasource.
            final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList("Invoice"));

            // Render the invoice as a PDF file.
            JasperReportsUtils.renderAsPdf(report, parameters, dataSource, pos);

            // return file.
            return pdfFile;
        }
        catch (final Exception e)
        {
            logger.error(String.format("An error occured during PDF creation: %s", e));
            throw new RuntimeException(e);
        }
    }

    // Fill template order params
    private Map<String, Object> parameters(InvoiceModel invoice) {
        final Map<String, Object> parameters = new HashMap<>();
        parameters.put("logo", getClass().getResourceAsStream(logo_path));
        parameters.put("invoice",  invoice);
        parameters.put("address",  invoice.getAddress());
        parameters.put("invoiceNo",  invoice.getInvoiceNo());
        parameters.put("billingFirstName",  invoice.getAddress().getBillingFirstName());
        parameters.put("billingLastName",  invoice.getAddress().getBillingLastName());
        parameters.put("billingStreetName",  invoice.getAddress().getBillingStreetName());
        parameters.put("billingTown",  invoice.getAddress().getBillingTown());
        parameters.put("billingCountry",  invoice.getAddress().getBillingCountry());
        parameters.put("billingPostalCode",  invoice.getAddress().getBillingPostalCode());
        parameters.put("shippingFirstName",  invoice.getAddress().getShippingFirstName());
        parameters.put("shippingLastName",  invoice.getAddress().getShippingLastName());
        parameters.put("shippingStreetName",  invoice.getAddress().getShippingStreetName());
        parameters.put("shippingTown",  invoice.getAddress().getShippingTown());
        parameters.put("shippingCountry",  invoice.getAddress().getShippingCountry());
        parameters.put("shippingPostalCode",  invoice.getAddress().getShippingPostalCode());
        parameters.put("entriesSize",  invoice.getAddress().getEntriesSize());
        parameters.put("tax",  invoice.getAddress().getSalesTax());
        parameters.put("invoiceDate",  invoice.getInvoiceDate());
        parameters.put("dueDate",  invoice.getDueDate());
        parameters.put("dueAmount",  invoice.getDueAmount());
        parameters.put("withDue",  invoice.getDueAmount()!=null? invoice.getDueAmount() + invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal() : invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal());
        parameters.put("grandTotal",  invoice.getAddress().getGrandTotal() );
        parameters.put("finalPrice",  (invoice.getAddress().getTotalPrice() + invoice.getAddress().getGrandTotal()));
        long diff = getDifferenceDays(invoice.getDueDate(), invoice.getInvoiceDate());
        parameters.put("notes", "1. Payment due in "+diff+" days");
        return parameters;
    }
    
    public static long getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    // Load invoice JRXML template
    private JasperReport loadTemplate() throws JRException {

        logger.info(String.format("Invoice template path : %s", invoice_template));

        final InputStream reportInputStream = getClass().getResourceAsStream(invoice_template);
        final JasperDesign jasperDesign = JRXmlLoader.load(reportInputStream);

        return JasperCompileManager.compileReport(jasperDesign);
    }

	public InvoiceModel getInvoiceById(String id) {
		return invoiceDao.getInvoiceById(id);
	}
	
	public InvoiceModel updateInvoice(InvoiceModel invoiceDetails) {
		setOrderEntryDetails(invoiceDetails.getAddress().getEntries(), invoiceDetails.getInvoiceNo());
		setAddressDetails(invoiceDetails.getAddress(), invoiceDetails.getInvoiceNo());
		invoiceDao.save(invoiceDetails);
		return invoiceDetails;
	}

}
