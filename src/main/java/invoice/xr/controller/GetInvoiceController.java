package invoice.xr.controller;

import invoice.xr.model.InvoiceModel;
import invoice.xr.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ownerMod")
public class GetInvoiceController {
    @Autowired
    private ReportService reportService;

    /**
     *  getInvoiceByDate is used for owner to create report based on Date
     *
     */
    @GetMapping("/createReportByDate")
    public ResponseEntity<List<InvoiceModel>> getInvoiceByDate(@RequestParam(value = "date") Date date){
        List<InvoiceModel> report= reportService.createReportByDate(date);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    /**
     *  getInvoiceByStatus is used for owner to create report based on payment status
     *
     */
    @GetMapping("/createReportByStatus")
    public  ResponseEntity<List<InvoiceModel>> getInvoiceByStatus(@RequestParam(value = "status")String status){
        List<InvoiceModel> report= reportService.createReportByStatus(status);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    /**
     *  getInvoiceByClientId is used for owner to create report based on payment status
     *
     */
    @GetMapping("/createReportByClientId")
    public  ResponseEntity<List<InvoiceModel>> getInvoiceByClientId(@RequestParam(value = "id")String id){
        List<InvoiceModel> report= reportService.createReportByClientId(id);
        if (report == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(report, HttpStatus.OK);
    }
}
