/**
 * 
 */
package invoice.xr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * CompanyModel is used to register new company.
 * 
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name="companyModel")
public class CompanyModel {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="companyId")
	private String companyId;
	
	@Column(name="companyName")
	private String companyName;
	
	@Column(name = "companyStreetName")
	private String companyStreetName;

	@Column(name = "companyPostalCode")
	private String companyPostalCode;

	@Column(name = "companyTown")
	private String companyTown;

	@Column(name = "companyCountry")
	private String companyCountry;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyStreetName() {
		return companyStreetName;
	}

	public void setCompanyStreetName(String companyStreetName) {
		this.companyStreetName = companyStreetName;
	}

	public String getCompanyPostalCode() {
		return companyPostalCode;
	}

	public void setCompanyPostalCode(String companyPostalCode) {
		this.companyPostalCode = companyPostalCode;
	}

	public String getCompanyTown() {
		return companyTown;
	}

	public void setCompanyTown(String companyTown) {
		this.companyTown = companyTown;
	}

	public String getCompanyCountry() {
		return companyCountry;
	}

	public void setCompanyCountry(String companyCountry) {
		this.companyCountry = companyCountry;
	}
	
}
