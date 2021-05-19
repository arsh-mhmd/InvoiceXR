package invoice.xr.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.AddressModel;

public interface AddressDao extends Repository<AddressModel, Integer> {

	void save(AddressModel addressModel);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from AddressModel addressModel where addressModel.id=:addressId")
	void deleteAddressById(Integer addressId);
}
