package invoice.xr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.OrderEntryModel;

public interface OrderEntryDao extends Repository<OrderEntryModel, Integer> {

	void save(List<OrderEntryModel> orderEntryModelList);


	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from OrderEntryModel where order_id=:order_id")
	void deleteEntriesByOrderId(Integer order_id);
	
}
