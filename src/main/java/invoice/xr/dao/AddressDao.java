package invoice.xr.dao;

import org.springframework.data.repository.Repository;

import invoice.xr.model.AddressModel;

public interface AddressDao extends Repository<AddressModel, Integer> {

	void save(AddressModel addressModel);
	
}
