package invoice.xr.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.net.SyslogOutputStream;
import invoice.xr.model.ClientUser;
import invoice.xr.service.RegistrationService;
import invoice.xr.model.Status;

/**
 * ClientRegistrationController allows Manager or Owner to create new client.
 * 
 * @author Arshath Mohammed
 *
 */
@RestController
public class ClientRegistrationController {

	@Autowired
	RegistrationService registrationService;

	@GetMapping("/")
	public String home() {
		return "Welcome";
	}

	@GetMapping("/showAllClient")
	public ResponseEntity<List<ClientUser>> getAllClient() {
		List<ClientUser> clientUsersList = registrationService.getAllClientDetail();
		return new ResponseEntity<>(clientUsersList, HttpStatus.OK);
	}

	@GetMapping("/findClient")
	public ResponseEntity<List<ClientUser>> findClientByClientName(@RequestParam(value = "clientName") String clientName) {
		List<ClientUser> clientUsersList = registrationService.findClientByClientName(clientName);
		return new ResponseEntity<>(clientUsersList, HttpStatus.OK);
	}
	
	@GetMapping("/selectClient")
	public ResponseEntity<ClientUser> findClientById(@RequestParam(value = "clientId") String clientId) {
		ClientUser clientUser = registrationService.findClientById(clientId);
		return new ResponseEntity<>(clientUser, HttpStatus.OK);
	}

	@PostMapping("/registerClient")
	public ResponseEntity<ClientUser> registerClient(@RequestBody ClientUser userDetails) {

		try {

			ClientUser responseClientUser = registrationService.registerNewClient(userDetails);
			return new ResponseEntity<>(responseClientUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/removeClient/{id}")
	public String removeClientById(@PathVariable("id") Integer id) {
		registrationService.removeClientById(id);
		return "Delete by id called";
	}

	@DeleteMapping("/removeClient")
	public String removeClientByClientName(@RequestParam(value = "firstName") String clientName) {
		registrationService.removeClientByClientName(clientName);
		return "Delete by name called";
	}

//	@GetMapping("/showAllInvoiceUser")
//	public ResponseEntity<List<InvoiceUser>> getAllInvoiceUser() {
//		List<InvoiceUser> invoiceUsersList = registrationService.getAllInvoiceUserDetail();
//		return new ResponseEntity<>(invoiceUsersList, HttpStatus.OK);
//	}
//
//	@GetMapping("/findInvoiceUser")
//	public ResponseEntity<List<InvoiceUser>> findInvoiceUserByName(@RequestParam(value = "fullName") String fullName) {
//		List<InvoiceUser> invoiceUsersList = registrationService.findInvoiceUserByName(fullName);
//		return new ResponseEntity<>(invoiceUsersList, HttpStatus.OK);
//	}
//
//	@PostMapping("/registerInvoiceUser")
//	public ResponseEntity<Status> registerInvoiceUser(@RequestBody InvoiceUser invoiceUserDetails) {
//		try {
//
//			if (registrationService.checkInvoiceUser(invoiceUserDetails)) {
//				registrationService.registerNewInvoiceUser(invoiceUserDetails);
//				return new ResponseEntity<>(Status.SUCCESS, HttpStatus.CREATED);
//			} else {
//				return new ResponseEntity<>(Status.USER_ALREADY_EXISTS, HttpStatus.CREATED);
//			}
//		} catch (Exception e) {
//			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//	}
//	
//	@PostMapping("/invoiceUser/login")
//    public Status invoiceUserLogin(@RequestBody InvoiceUser loginUser) {
//        List<InvoiceUser> users = registrationService.getAllInvoiceUserDetail();
//        for (InvoiceUser other : users) {
//            if (other.equals(loginUser)) {
//            	loginUser.setLoggedIn(true);
//                registrationService.registerNewInvoiceUser(loginUser);
//                return Status.SUCCESS;
//            }
//        }
//        return Status.FAILURE;
//    }
//
//	@PostMapping("/invoiceUser/logout")
//    public Status invoiceUserLogout(@RequestBody InvoiceUser logoutUser) {
//        List<InvoiceUser> users = registrationService.getAllInvoiceUserDetail();
//        for (InvoiceUser other : users) {
//            if (other.equals(logoutUser)) {
//            	logoutUser.setLoggedIn(false);
//            	registrationService.registerNewInvoiceUser(logoutUser);
//                return Status.SUCCESS;
//            }
//        }
//        return Status.FAILURE;
//    }
//	
//	@DeleteMapping("/removeInvoiceUser/{id}")
//	public String removeInvoiceUserById(@PathVariable("id") Integer id) {
//		registrationService.removeInvoiceUserById(id);
//		return "Delete by id called";
//	}
//
//	@DeleteMapping("/removeInvoiceUser")
//	public String removeInvoiceUserByName(@RequestParam(value = "fullName") String fullName) {
//		registrationService.removeInvoiceUserByName(fullName);
//		return "Delete by name called";
//	}
}
