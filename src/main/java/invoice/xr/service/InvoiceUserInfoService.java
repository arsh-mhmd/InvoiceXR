package invoice.xr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.InvoiceUserInfo;
import invoice.xr.dao.InvoiceUserInfoDao;

/**
 * @author Arshath Mohammed
 *
 */
@Repository
@Transactional
public class InvoiceUserInfoService {

	@Autowired
	private InvoiceUserInfoDao invoiceUserInfoDao;

	public InvoiceUserInfo getUserInfoByUserName(String userName) {
		short enabled = 1;
		return invoiceUserInfoDao.findByUserNameAndEnabled(userName, enabled);
	}

	public List<InvoiceUserInfo> getAllActiveUserInfo() {
		return invoiceUserInfoDao.findAll();
	}

	public InvoiceUserInfo getUserInfoById(Integer id) {
		return invoiceUserInfoDao.findById(id);
	}

	public InvoiceUserInfo addUser(InvoiceUserInfo userInfo) {
		userInfo.setPassword(new BCryptPasswordEncoder().encode(userInfo.getPassword()));
		return invoiceUserInfoDao.save(userInfo);
	}

	public InvoiceUserInfo updateUser(Integer id, InvoiceUserInfo userRecord) {
		InvoiceUserInfo userInfo = invoiceUserInfoDao.findById(id);
		userInfo.setUserName(userRecord.getUserName());
		userInfo.setPassword(userRecord.getPassword());
		userInfo.setRole(userRecord.getRole());
		userInfo.setEnabled(userRecord.getEnabled());
		return invoiceUserInfoDao.save(userInfo);
	}

	public void deleteUser(Integer id) {
		invoiceUserInfoDao.deleteById(id);
	}

	public InvoiceUserInfo updatePassword(Integer id, InvoiceUserInfo userRecord) {
		InvoiceUserInfo userInfo = invoiceUserInfoDao.findById(id);
		userInfo.setPassword(userRecord.getPassword());
		return invoiceUserInfoDao.save(userInfo);
	}

	public InvoiceUserInfo updateRole(Integer id, InvoiceUserInfo userRecord) {
		InvoiceUserInfo userInfo = invoiceUserInfoDao.findById(id);
		userInfo.setRole(userRecord.getRole());
		return invoiceUserInfoDao.save(userInfo);
	}
	
	public void removeInvoiceUserByUserName(String userName) {
		InvoiceUserInfo invoiceUserInfo = invoiceUserInfoDao.findByUserName(userName);
		invoiceUserInfoDao.delete(invoiceUserInfo);
	}

	public void removeInvoiceUserById(Integer id) {
		invoiceUserInfoDao.deleteById(id);
	}
}


