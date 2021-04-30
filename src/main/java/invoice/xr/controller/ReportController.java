package invoice.xr.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.springframework.http.MediaType.APPLICATION_PDF;
import static java.lang.String.format;

import invoice.xr.model.InvoiceModel;
import invoice.xr.service.InvoiceService;

/**
 * ReportController allows Manager or Owner to create pdf.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
public class ReportController {

	@Autowired
	InvoiceService invoiceService;
	
	static private Logger logger = LogManager.getLogger(ReportController.class);


	// generate invoice pdf
    @GetMapping(value = "/generate", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> invoiceGenerate(@RequestParam(name = "invoiceNo", defaultValue = "IN-20210412213814") String invoiceNo) throws IOException {
        logger.info("Start invoice generation...");
        final InvoiceModel invoice = invoiceService.getInvoice(invoiceNo);
        final File invoicePdf = invoiceService.generateInvoiceFor(invoice);
        logger.info("Invoice generated successfully...");

        final HttpHeaders httpHeaders = getHttpHeaders(invoiceNo, invoicePdf);
        return new ResponseEntity<>(new InputStreamResource(new FileInputStream(invoicePdf)), httpHeaders, HttpStatus.OK);
    }

    private HttpHeaders getHttpHeaders(String invoiceNo, File invoicePdf) {
        HttpHeaders respHeaders = new HttpHeaders();
        respHeaders.setContentType(APPLICATION_PDF);
        respHeaders.setContentLength(invoicePdf.length());
        respHeaders.setContentDispositionFormData("attachment", format("%s.pdf", invoiceNo));
        return respHeaders;
    } 
	
	
}
