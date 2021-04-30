package invoice.xr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
<<<<<<< Updated upstream
=======
<<<<<<< HEAD

=======
>>>>>>> Stashed changes
	
	@GetMapping("/getAllInvoices")
	public ResponseEntity<List<InvoiceModel>> getAllInvoices() {
		List<InvoiceModel> invoiceList = invoiceService.getAllInvoices();
		return new ResponseEntity<>(invoiceList, HttpStatus.OK);
	}
	
<<<<<<< Updated upstream
=======
>>>>>>> master
>>>>>>> Stashed changes
}
