package invoice.xr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.OrderEntryModel;
import invoice.xr.model.QuoteEntryModel;

public interface QuoteEntryDao extends Repository<QuoteEntryModel, Integer> {

	void save(List<QuoteEntryModel> quoteEntryModelList);


	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from QuoteEntryModel where quote_entry_id=:quote_entry_id")
	void deleteEntriesByQuoteEntryId(String quote_entry_id);
	
}
