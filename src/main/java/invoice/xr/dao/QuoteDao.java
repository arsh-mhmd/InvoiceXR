package invoice.xr.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceModel;
import invoice.xr.model.QuoteModel;

public interface QuoteDao extends Repository<QuoteModel, Integer> {

	void save(QuoteModel quoteModel);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from QuoteModel quoteModel where quoteModel.id=:id")
	void deleteQuoteById(@Param("id") String id);

	@Query("SELECT quoteModel from QuoteModel quoteModel where quoteModel.id IN (:id)")
	@Transactional(readOnly = true)
	QuoteModel getQuoteById(String id);
	
	@Query("SELECT quoteModel from QuoteModel quoteModel where quoteModel.quoteNo IN (:quoteNo)")
	@Transactional(readOnly = true)
	QuoteModel getQuoteByQuoteNo(String quoteNo);
	
	@Query("SELECT quoteModel from QuoteModel quoteModel")
	@Transactional(readOnly = true)
	List<QuoteModel> findAllQuotes();
	

	
}
