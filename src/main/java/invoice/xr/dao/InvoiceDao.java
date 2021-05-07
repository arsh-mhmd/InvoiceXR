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

public interface InvoiceDao extends Repository<InvoiceModel, Integer> {

	void save(InvoiceModel invoiceModel);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from InvoiceModel invoiceModel where invoiceModel.id=:id")
	void deleteInvoiceById(@Param("id") String id);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.id IN (:id)")
	@Transactional(readOnly = true)
	InvoiceModel getAddressId(String id);
	
	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.id IN (:id)")
	@Transactional(readOnly = true)
	InvoiceModel getInvoiceById(String id);
	
	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.invoiceNo IN (:invoiceNo)")
	@Transactional(readOnly = true)
	InvoiceModel getInvoiceByInvoiceNo(String invoiceNo);
	
	@Query("SELECT invoiceModel from InvoiceModel invoiceModel")
	@Transactional(readOnly = true)
	List<InvoiceModel> findAllInvoices();
	
	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.status=:status")
	@Transactional(readOnly = true)
	List<InvoiceModel> getReportByStatus(String status);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.clientId=:ClientId")
	@Transactional(readOnly = true)
	List<InvoiceModel>  getReportByClientId(String ClientId);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.invoiceDate<:date")
	@Transactional(readOnly = true)
	List<InvoiceModel>  getReportByDate(Date date);

	
}
