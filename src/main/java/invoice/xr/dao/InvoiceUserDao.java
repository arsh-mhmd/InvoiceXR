package invoice.xr.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.InvoiceUser;

public interface InvoiceUserDao extends Repository<InvoiceUser, Integer> {
	
	
	@Query("SELECT invoiceUser from InvoiceUser invoiceUser where invoiceUser.fullName IN (:fullName)")
	@Transactional(readOnly = true)
	List<InvoiceUser> findInvoiceUserByName(@Param("fullName") String fullName);
	
	void save(InvoiceUser invoiceUser);
	
	void deleteById(Integer id);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from InvoiceUser invoiceUser where invoiceUser.fullName=:fullName")
	void deleteInvoiceUserByName(@Param("fullName") String fullName);
	
	@Query("SELECT invoiceUser from InvoiceUser invoiceUser")
	@Transactional(readOnly = true)
	List<InvoiceUser> findAllInvoiceUser();
	
	
	  
}
