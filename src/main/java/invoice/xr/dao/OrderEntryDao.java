package invoice.xr.dao;

import java.util.List;

import org.springframework.data.repository.Repository;

import invoice.xr.model.OrderEntryModel;

public interface OrderEntryDao extends Repository<OrderEntryModel, Integer> {

	void save(List<OrderEntryModel> orderEntryModelList);
	
}
