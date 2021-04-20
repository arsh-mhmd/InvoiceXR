package invoice.xr.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import invoice.xr.model.InvoiceUserInfo;
import invoice.xr.service.InvoiceUserInfoService;

/** 
 * InvoiceUserController has all the functionalities of Manager.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
@RequestMapping("/managerSpace")
public class InvoiceUserController {
	@Autowired
	private InvoiceUserInfoService userService;

	

	@PostMapping("/user")
	public InvoiceUserInfo addUser(@RequestBody InvoiceUserInfo userRecord) {
		return userService.addUser(userRecord);
	}
	
	/**
	 * updateUser is used to update invoice user details
	 * 
	 * @param userRecord
	 * @param id
	 * @return
	 */
	@PutMapping("/updateInvoiceUser/{id}")
	public InvoiceUserInfo updateUser(@RequestBody InvoiceUserInfo userRecord, @PathVariable Integer id) {
		return userService.updateUser(id,userRecord);
	}
	
	/**
	 * updateUserPassword is used to update manager password
	 * 
	 * @param userRecord
	 * @param id
	 * @return
	 */
	@PutMapping("/user/changePassword/{id}")
	public InvoiceUserInfo updateUserPassword(@RequestBody InvoiceUserInfo userRecord, @PathVariable Integer id) {
		return userService.updatePassword(id,userRecord);
	}

	

	
}

