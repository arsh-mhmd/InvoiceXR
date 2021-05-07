/**
 * 
 */
package invoice.xr.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import invoice.xr.dao.RegisterDao;
import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceUserInfo;


/**
 * @author Arshath Mohammed
 *
 */
@Service
public class RegistrationService {

	@Autowired
	RegisterDao registerDao;
	
	public ClientUser registerNewClient(ClientUser userDetails) {
		userDetails.setClientId(generateClientId());
		registerDao.save(userDetails);
		return userDetails;
	}
	
	private String generateClientId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}
	
	public void removeClientById(Integer id) {
		registerDao.deleteById(id);
	}
	
	public void removeClientByClientId(String clientId) {
		registerDao.deleteClientByClientId(clientId);
	}
	
	public List<ClientUser> getAllClientDetail() {
		return registerDao.findAllClient();
	}
	
	public List<ClientUser> findClientByClientName(String firstName) {
		return registerDao.findClientByClientName(firstName);
	}
	
	public ClientUser findClientById(String clientId) {
		return registerDao.findClientById(clientId);
	}

	public ClientUser updateClient(ClientUser userRecord) {
		registerDao.save(userRecord);
		return userRecord;
	}
	
}
