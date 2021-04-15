/**
 * 
 */
package invoice.xr.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import invoice.xr.dao.RegisterDao;
import invoice.xr.model.ClientUser;


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
		//System.out.print(responseClientUser.getFirstName());
		return userDetails;
	}
	
	private String generateClientId() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}
	
	public void removeClientById(Integer id) {
		registerDao.deleteById(id);
	}
	
	public void removeClientByClientName(String clientName) {
		registerDao.deleteClientByClientName(clientName);
	}
	
	public List<ClientUser> getAllClientDetail() {
		return registerDao.findAllClient();
	}
	
	public List<ClientUser> findClientByClientName(String clientName) {
		return registerDao.findClientByClientName(clientName);
	}
	
	public ClientUser findClientById(String clientId) {
		return registerDao.findClientById(clientId);
	}
	
}
