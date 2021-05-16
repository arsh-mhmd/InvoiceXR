package invoice.xr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
import invoice.xr.model.QuoteModel;
import invoice.xr.service.InvoiceService;
import invoice.xr.service.QuoteService;


/**
 * InvoiceController allows Manager or Owner to create invoice.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
public class ClientQuoteController {

	@Autowired
	QuoteService quoteService;
	
	@GetMapping("/approveQuote")
    public ResponseEntity<QuoteModel> approveQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		QuoteModel quote = quoteService.getQuote(quoteNo);
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }
	
	@GetMapping("/acceptQuote")
    public String acceptQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		quoteService.acceptQuote(quoteNo);
        return "Quote Accepted";
    }
	
	@GetMapping("/declineQuote")
    public String declineQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		quoteService.declineQuote(quoteNo);
        return "Quote Accepted";
    }
	
}
