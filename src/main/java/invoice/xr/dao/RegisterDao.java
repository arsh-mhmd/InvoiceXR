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
	
	
	
	@Query("SELECT clientUser from ClientUser clientUser where clientUser.firstName IN (:firstName)")
	@Transactional(readOnly = true)
	List<ClientUser> findClientByClientName(@Param("firstName") String firstName);
	
	@Query("SELECT clientUser from ClientUser clientUser where clientUser.clientId IN (:clientId)")
	@Transactional(readOnly = true)
	ClientUser findClientById(@Param("clientId") String clientId);
	
	@Query("SELECT clientUser from ClientUser clientUser where clientUser.companyId IN (:companyId)")
	@Transactional(readOnly = true)
	List<ClientUser>  findClientByCompanyId(@Param("companyId") String companyId);

	@Query("SELECT clientUser from ClientUser clientUser where clientUser.id IN (:id)")
	@Transactional(readOnly = true)
	ClientUser findClientByMainId(@Param("id") int id);

	void save(ClientUser clientUser);
	
	void deleteById(Integer id);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from ClientUser clientUser where clientUser.clientId=:clientId")
	void deleteClientByClientId(@Param("clientId") String clientId);
	
	@Query("SELECT clientUser from ClientUser clientUser")
	@Transactional(readOnly = true)
	List<ClientUser> findAllClient();
	
	
	  
}
