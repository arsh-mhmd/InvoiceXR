package invoice.xr.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.ClientUser;


public interface RegisterDao extends Repository<ClientUser, Integer> {
	
	
	
	@Query("SELECT clientUser from ClientUser clientUser where clientUser.lastName IN (:lastName)")
	@Transactional(readOnly = true)
	List<ClientUser> findClientByLastName(@Param("lastName") String lastName);
	
	void save(ClientUser clientUser);
	
	void deleteById(Integer id);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from ClientUser clientUser where clientUser.firstName=:firstName")
	void deleteClientByFirstName(@Param("firstName") String firstName);
	
	@Query("SELECT clientUser from ClientUser clientUser")
	@Transactional(readOnly = true)
	List<ClientUser> findAllClient();
	
	
	  
}
