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
import org.springframework.web.bind.annotation.DeleteMapping;
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
public class QuoteController {

	@Autowired
	QuoteService quoteService;
	
	@PostMapping("/createQuote")
	public ResponseEntity<QuoteModel> createQuote(@RequestBody QuoteModel quote) {

		quoteService.createNewQuote(quote);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
//		try {
//			
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
	}
	
	@GetMapping("/getAllQuotes")
	public ResponseEntity<List<QuoteModel>> getAllQuotes() {
		List<QuoteModel> quotesList = quoteService.getAllQuotes();
		return new ResponseEntity<>(quotesList, HttpStatus.OK);
	}
	
	
	@GetMapping("/getQuote")
	public ResponseEntity<QuoteModel> getQuote(@RequestParam(value = "quoteNo") String quoteNo) {
		QuoteModel quote = quoteService.getQuote(quoteNo);
		return new ResponseEntity<>(quote, HttpStatus.OK);
	}
	
	@PostMapping("/convertQuote")
	public ResponseEntity<QuoteModel> convertQuote(@RequestBody QuoteModel quote) {

		quoteService.createInvoiceFromQuote(quote);
		quoteService.updateQuote(quote);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	/**
	 * 
	 * Delete Quote method is used to delete quote from the table
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/removeQuoteById")
	public String removeQuoteById(@RequestParam(value = "id") String id) {
		quoteService.removeQuote(id);
		return "Invoice User Deleted";
	}
	
	@PostMapping("/updateQuote")
	public ResponseEntity<QuoteModel> updateQuote(@RequestBody QuoteModel quote) {
		QuoteModel quoteList = quoteService.updateQuote(quote);
		return new ResponseEntity<>(quoteList, HttpStatus.CREATED);
	}
}
