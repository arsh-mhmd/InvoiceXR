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
import org.springframework.web.bind.annotation.RestController;

import invoice.xr.model.InvoiceUserInfo;
import invoice.xr.service.InvoiceUserInfoService;

@RestController
public class InvoiceUserController {
	@Autowired
	private InvoiceUserInfoService userService;

	@GetMapping("/user")
	public Object getAllUser(@RequestHeader HttpHeaders requestHeader) {
		List<InvoiceUserInfo> userInfos = userService.getAllActiveUserInfo();
		if (userInfos == null || userInfos.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return userInfos;
	}

	@PostMapping("/user")
	public InvoiceUserInfo addUser(@RequestBody InvoiceUserInfo userRecord) {
		return userService.addUser(userRecord);
	}

	@PutMapping("/user/{id}")
	public InvoiceUserInfo updateUser(@RequestBody InvoiceUserInfo userRecord, @PathVariable Integer id) {
		return userService.updateUser(id,userRecord);
	}
	
	@PutMapping("/user/changePassword/{id}")
	public InvoiceUserInfo updateUserPassword(@RequestBody InvoiceUserInfo userRecord, @PathVariable Integer id) {
		return userService.updatePassword(id,userRecord);
	}
	
	@PutMapping("/user/changeRole/{id}")
	public InvoiceUserInfo updateUserRole(@RequestBody InvoiceUserInfo userRecord, @PathVariable Integer id) {
		return userService.updateRole(id,userRecord);
	}

	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Integer id) {
		userService.deleteUser(id);
	}

	@GetMapping("/user/{id}")
	public ResponseEntity<InvoiceUserInfo> getUserById(@PathVariable Integer id) {
		InvoiceUserInfo userInfo = userService.getUserInfoById(id);
		if (userInfo == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(userInfo, HttpStatus.OK);
	}
}

