package invoice.xr.controller;

import java.util.Date;
import java.util.List;

import invoice.xr.model.InvoiceModel;
import invoice.xr.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import invoice.xr.model.InvoiceUserInfo;
import invoice.xr.service.InvoiceService;
import invoice.xr.service.InvoiceUserInfoService;

/**
 * OwnerModuleController has all the functionalities of Owner.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
@RequestMapping("/ownerMod")
public class OwnerModuleController {

	@Autowired
	private InvoiceUserInfoService userService;
	
	@Autowired
	private InvoiceService invoiceService;
	
	@GetMapping("/")
	public String home() {
		return "Welcome Owner";
	}

	@Autowired
	private ReportService reportService;
	
	
	/**
	 * addUser method is used to create new manager
	 * 
	 * @param userRecord
	 * @return userRecord
	 */
	@PostMapping("/createUser")
	public InvoiceUserInfo addUser(@RequestBody InvoiceUserInfo userRecord) {
		return userService.addUser(userRecord);
	}
	
	/**
	 * 
	 * removeInvoice method is used to delete invoice from the table
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/removeInvoice")
	public String removeInvoiceById(@RequestParam(value = "id") String id) {
		invoiceService.removeInvoiceById(id);
		return "Invoice Deleted";
	}
	
	
	/**
	 * getAllInvoiceUser is used to get all managers list.
	 * 
	 * @param requestHeader
	 * @return
	 */
	@GetMapping("/getManagers")
	public Object getAllInvoiceUser(@RequestHeader HttpHeaders requestHeader) {
		List<InvoiceUserInfo> userInfos = userService.getAllActiveUserInfo();
		if (userInfos == null || userInfos.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return userInfos;
	}
	
	/**
	 * 
	 * removeInvoiceUserById method is used to delete manager from the table
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/removeInvoiceUserById")
	public String removeInvoiceUserById(@RequestParam(value = "id") Integer id) {
		userService.removeInvoiceUserById(id);
		return "Invoice User Deleted";
	}
	
	/**
	 * 
	 * removeInvoiceUserByUserName method is used to delete manager from the table
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/removeInvoiceUserByName")
	public String removeInvoiceUserByUserName(@RequestParam(value = "userName") String userName) {
		userService.removeInvoiceUserByUserName(userName);
		return "Invoice User Deleted";
	}
	
	/**
	 * getInvoiceUserById is used fetch manager by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/getInvoiceUser")
	public ResponseEntity<InvoiceUserInfo> getInvoiceUserById(@RequestParam(value = "id") Integer id) {
		InvoiceUserInfo userInfo = userService.getUserInfoById(id);
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}


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
	public  ResponseEntity<List<InvoiceModel>> getInvoiceByClientId(@RequestParam(value = "id")String id) {
		List<InvoiceModel> report = reportService.createReportByClientId(id);
		if (report == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(report, HttpStatus.OK);
	}


}
