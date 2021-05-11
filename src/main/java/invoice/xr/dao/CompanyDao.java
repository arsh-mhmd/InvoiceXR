package invoice.xr.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import invoice.xr.model.ClientUser;
import invoice.xr.model.CompanyModel;


public interface CompanyDao extends Repository<CompanyModel, Integer> {
	
	
	
	@Query("SELECT companyModel from CompanyModel companyModel where companyModel.companyId IN (:companyId)")
	@Transactional(readOnly = true)
	CompanyModel findCompanyById(@Param("companyId") String companyId);

	void save(CompanyModel companyModel);
	
	void deleteById(Integer id);
	
	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from CompanyModel companyModel where companyModel.companyId=:companyId")
	void deleteCompanyByCompanyId(@Param("companyId") String companyId);
	
	@Query("SELECT companyModel from CompanyModel companyModel")
	@Transactional(readOnly = true)
	List<CompanyModel> findAllCompanies();

	@Modifying
	@Transactional(readOnly = false)
	@Query("delete from CompanyModel companyModel where companyModel.id=:id")
	void removeCompanyById(Integer id);
	
	
	  
}
