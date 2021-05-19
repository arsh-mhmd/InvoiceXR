package invoice.xr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import invoice.xr.model.PaymentRecordModel;
import invoice.xr.service.PaymentRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceModel;
import invoice.xr.service.InvoiceService;


/**
 * InvoiceController allows Manager or Owner to create invoice.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	@Autowired
	PaymentRecordService paymentRecordService;
	
	/**createInvoice is used to create invoice
	 * 
	 * @param invoiceDetails
	 * @return InvoiceModel
	 */
	@PostMapping("/createInvoice")
	public ResponseEntity<InvoiceModel> createInvoice(@RequestBody InvoiceModel invoiceDetails) {

		invoiceService.createNewInvoice(invoiceDetails);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	/**getAllInvoices is used to fetch all invoices
	 * 
	 * @return List<InvoiceModel>
	 */
	@GetMapping("/getAllInvoices")
	public ResponseEntity<List<InvoiceModel>> getAllInvoices() {
		List<InvoiceModel> invoiceList = invoiceService.getAllInvoices();
		return new ResponseEntity<>(invoiceList, HttpStatus.OK);
	}
	
	/**getInvoice is used to fetch invoice by id
	 * 
	 * @param id
	 * @return InvoiceModel
	 */
	@GetMapping("/getInvoice")
    public ResponseEntity<InvoiceModel> getInvoice(@RequestParam(name = "id") String id) {
        InvoiceModel invoice = invoiceService.getInvoiceById(id);
        return new ResponseEntity<>(invoice, HttpStatus.OK);
    }
	
	/**updateInvoice is used to update invoice details
	 * 
	 * @param invoiceDetails
	 * @return InvoiceModel
	 */
	@PostMapping("/updateInvoice")
	public ResponseEntity<InvoiceModel> updateInvoice(@RequestBody InvoiceModel invoiceDetails) {
		InvoiceModel invoice = invoiceService.updateInvoice(invoiceDetails);
		return new ResponseEntity<>(invoice, HttpStatus.CREATED);
	}
	
	/**getAllPaymentRecord is used to get payment record
	 * @return List<PaymentRecordModel>
	 */
	@GetMapping("/getPaymentRecord")
	public ResponseEntity<List<PaymentRecordModel>> getAllPaymentRecord(){
		List<PaymentRecordModel> paymentRecordModels = paymentRecordService.getAllPaymentList();
		return new ResponseEntity<>(paymentRecordModels,HttpStatus.OK);
	}
}
