package invoice.xr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import invoice.xr.model.InvoiceModel;
import invoice.xr.service.InvoiceService;

@RestController
public class InvoiceController {

	@Autowired
	InvoiceService invoiceService;
	
	@PostMapping("/createInvoice")
	public ResponseEntity<InvoiceModel> createInvoice(@RequestBody InvoiceModel invoiceDetails) {

		invoiceService.createNewInvoice(invoiceDetails);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
//		try {
//			
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
}
