package invoice.xr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

import invoice.xr.model.ClientUser;
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
	@PostMapping("/updateProfile")
	public InvoiceUserInfo updateUser(@RequestBody InvoiceUserInfo userRecord) {
		return userService.updateUser(userRecord);
	}
	
	@GetMapping("/getUserProfile")
	public ResponseEntity<InvoiceUserInfo> findUserByUserName(@RequestParam(value = "userName") String userName) {
		InvoiceUserInfo user = userService.getUserInfoByUserName(userName);
		return new ResponseEntity<InvoiceUserInfo>(user, HttpStatus.OK);
	}
	
	@GetMapping("/verifyUser")
	public Boolean verifyUser(@RequestParam(value = "userName") String userName,
			@RequestParam(value = "enpassword") String enpassword,
			@RequestParam(value = "rawpassword") String rawpassword) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String p = bCryptPasswordEncoder.encode(rawpassword);
		InvoiceUserInfo user = userService.getUserInfoByUserName(userName);
		System.out.println(user.getPassword());
		System.out.println(p);
		if(bCryptPasswordEncoder.matches(rawpassword, user.getPassword())) {
			return true;
		}
		else {
			return false;
		}
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

