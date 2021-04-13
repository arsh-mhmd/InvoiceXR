package invoice.xr.service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import invoice.xr.model.AddressModel;
import invoice.xr.model.InvoiceModel;
import invoice.xr.model.OrderEntryModel;
import invoice.xr.dao.InvoiceDao;
import invoice.xr.dao.AddressDao;
import invoice.xr.dao.OrderEntryDao;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private OrderEntryDao orderEntryDao;

	public void createNewInvoice(InvoiceModel invoiceDetails) {
		invoiceDetails.setInvoiceNo(generateInvoiceNo(invoiceDetails.getInvoiceDate()));
		invoiceDetails.setId(generateInvoiceId());
		invoiceDetails.setClientId("clientId");
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

}
