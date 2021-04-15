package invoice.xr.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import invoice.xr.model.InvoiceUserInfo;

@Repository
@Transactional
public interface InvoiceUserInfoDao extends CrudRepository<InvoiceUserInfo, String> {
	public InvoiceUserInfo findByUserNameAndEnabled(String userName, short enabled);
	
	public InvoiceUserInfo findByUserName(String userName);
	
	public List<InvoiceUserInfo> findAll();

	public InvoiceUserInfo findById(Integer id);

	@Override
	public InvoiceUserInfo save(InvoiceUserInfo userInfo);

	public void deleteById(Integer id);
	
	public void delete(InvoiceUserInfo invoiceUserInfo);
}


