package invoice.xr.dao;

import org.springframework.data.repository.Repository;

import invoice.xr.model.InvoiceModel;

public interface InvoiceDao extends Repository<InvoiceModel, Integer> {

	void save(InvoiceModel invoiceModel);
	
}
