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
 * ClientQuoteController is used to get response about the quote from client.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
public class ClientQuoteController {

	@Autowired
	QuoteService quoteService;
	
	/**getQuote is used to fetch quote details by quote no
	 * 
	 * @param quoteNo
	 * 
	 * @return QuoteModel
	 */
	@GetMapping("/approveQuote")
    public ResponseEntity<QuoteModel> getQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		QuoteModel quote = quoteService.getQuote(quoteNo);
        return new ResponseEntity<>(quote, HttpStatus.OK);
    }
	
	/**acceptQuote is used to set acceptance response from client
	 * 
	 * @param quoteNo
	 */
	@GetMapping("/acceptQuote")
    public void acceptQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		quoteService.acceptQuote(quoteNo);
    }
	
	/**declineQuote is used to set decline response from client
	 * 
	 * @param quoteNo
	 */
	@GetMapping("/declineQuote")
    public void declineQuote(@RequestParam(name = "quoteNo") String quoteNo) {
		quoteService.declineQuote(quoteNo);
    }
	
}
