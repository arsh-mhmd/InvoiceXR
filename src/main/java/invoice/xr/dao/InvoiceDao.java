package invoice.xr.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.InvoiceModel;

import java.util.Date;
import java.util.List;

public interface InvoiceDao extends Repository<InvoiceModel, Integer> {

	void save(InvoiceModel invoiceModel);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from InvoiceModel invoiceModel where invoiceModel.id=:id")
	void deleteInvoiceById(@Param("id") String id);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.id IN (:id)")
	@Transactional(readOnly = true)
	InvoiceModel getAddressId(String id);
	

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.status=:status")
	@Transactional(readOnly = true)
	List<InvoiceModel> getReportByStatus(String status);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.clientId=:ClientId")
	@Transactional(readOnly = true)
	List<InvoiceModel>  getReportByClientId(String ClientId);

	@Query("SELECT invoiceModel from InvoiceModel invoiceModel where invoiceModel.invoiceDate<:date")
	@Transactional(readOnly = true)
	List<InvoiceModel>  getReportByDate(Date date);

//	DELETE FROM `invoicexr`.`invoice_model` WHERE (`id` = '03276b95-d5b9-4b14-8534-3b1814ef5bb0');
//	DELETE FROM `invoicexr`.`address_model` WHERE (`id` = '46');
//
//	DELETE FROM `invoicexr`.`order_entry_model` WHERE (`id` = '47');
//	DELETE FROM `invoicexr`.`order_entry_model` WHERE (`id` = '48');
//	DELETE FROM `invoicexr`.`order_entry_model` WHERE (`id` = '49');
//	DELETE FROM `invoicexr`.`order_entry_model` WHERE (`id` = '51')
}
