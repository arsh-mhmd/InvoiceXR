/**
 * 
 */
package invoice.xr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import invoice.xr.dao.InvoiceUserDao;
import invoice.xr.dao.RegisterDao;
import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceUser;


/**
 * @author arshm
 *
 */
@Service
public class RegistrationService {

	@Autowired
	RegisterDao registerDao;
	
	@Autowired
	InvoiceUserDao invoiceUserDao;
	
	public ClientUser registerNewClient(ClientUser userDetails) {
		registerDao.save(userDetails);
		//System.out.print(responseClientUser.getFirstName());
		return userDetails;
	}
	
	public void removeClientById(Integer id) {
		registerDao.deleteById(id);
	}
	
	public void removeClientByFirstName(String firstName) {
		registerDao.deleteClientByFirstName(firstName);
	}
	
	public List<ClientUser> getAllClientDetail() {
		return registerDao.findAllClient();
	}
	
	public List<ClientUser> findClientByName(String lastName) {
		return registerDao.findClientByLastName(lastName);
	}
	
	public InvoiceUser registerNewInvoiceUser(InvoiceUser invoiceUserDetails) {
		invoiceUserDao.save(invoiceUserDetails);
		//System.out.print(responseClientUser.getFirstName());
		return invoiceUserDetails;
	}
	
	public void removeInvoiceUserById(Integer id) {
		invoiceUserDao.deleteById(id);
	}
	
	public void removeInvoiceUserByName(String fullName) {
		invoiceUserDao.deleteInvoiceUserByName(fullName);
	}
	
	public List<InvoiceUser> getAllInvoiceUserDetail() {
		return invoiceUserDao.findAllInvoiceUser();
	}
	
	public List<InvoiceUser> findInvoiceUserByName(String fullName) {
		return invoiceUserDao.findInvoiceUserByName(fullName);
	}
}
