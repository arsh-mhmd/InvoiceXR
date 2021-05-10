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
import invoice.xr.model.CompanyModel;
import invoice.xr.model.InvoiceUserInfo;
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
	
	@GetMapping("/showAllCompanies")
	public ResponseEntity<List<CompanyModel>> getAllCompanies() {
		List<CompanyModel> companyList = registrationService.getAllCompaniesList();
		return new ResponseEntity<>(companyList, HttpStatus.OK);
	}

	@GetMapping("/findClient")
	public ResponseEntity<List<ClientUser>> findClientByClientName(@RequestParam(value = "firstName") String firstName) {
		List<ClientUser> clientUsersList = registrationService.findClientByClientName(firstName);
		return new ResponseEntity<>(clientUsersList, HttpStatus.OK);
	}
	
	@GetMapping("/findClientByCompanyId")
	public ResponseEntity<List<ClientUser>> findClientByCompanyId(@RequestParam(value = "companyId") String companyId) {
		List<ClientUser> clientUsersList = registrationService.findClientByCompanyId(companyId);
		return new ResponseEntity<>(clientUsersList, HttpStatus.OK);
	}
	
	@GetMapping("/findCompanyByCompanyId")
	public ResponseEntity<CompanyModel> findCompanyByCompanyId(@RequestParam(value = "companyId") String companyId) {
		CompanyModel companyDetails = registrationService.findCompanyByCompanyId(companyId);
		return new ResponseEntity<>(companyDetails, HttpStatus.OK);
	}
	
	@GetMapping("/selectClient")
	public ResponseEntity<ClientUser> findClientById(@RequestParam(value = "clientId") String clientId) {
		ClientUser clientUser = registrationService.findClientById(clientId);
		return new ResponseEntity<>(clientUser, HttpStatus.OK);
	}
	
	/**
	 * updateUser is used to update invoice user details
	 * 
	 * @param userRecord
	 * @param id
	 * @return
	 */
	@PostMapping("/updateClient")
	public ClientUser updateUser(@RequestBody ClientUser userRecord) {
		return registrationService.updateClient(userRecord);
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
	
	@PostMapping("/registerCompany")
	public ResponseEntity<CompanyModel> registerCompany(@RequestBody CompanyModel companyDetails) {

		try {

			CompanyModel companyModel = registrationService.registerNewCompany(companyDetails);
			return new ResponseEntity<>(companyModel, HttpStatus.CREATED);
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
	public String removeClientByClientId(@RequestParam(value = "clientId") String clientId) {
		registrationService.removeClientByClientId(clientId);
		return "Delete by name called";
	}

}
