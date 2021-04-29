package invoice.xr.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Model to create new invoice. address_id is the foreign key of AddressModel.
 * 
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name = "InvoiceModel")
public class InvoiceModel {

	@Id
	private String id;

	@Column(name = "invoiceNo")
	private String invoiceNo;

	@Column(name = "invoiceDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date invoiceDate;

	@Column(name = "dueDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dueDate;

	@Column(name = "clientId")
	private String clientId;

	@Column(name = "companyName")
	private String companyName;

	@Column(name = "companyStreetName")
	private String companyStreetName;

	@Column(name = "companyPostalCode")
	private String companyPostalCode;

	@Column(name = "companyTown")
	private String companyTown;

	@Column(name = "companyCountry")
	private String companyCountry;

	@Column(name = "dueAmount")
	private String dueAmount;

	@Column(name = "status")
	private String status;
	
	@Column(name = "createdBy")
	private String createdBy;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	AddressModel address;


	/*
	 * public InvoiceModel(String userId, List<OrderEntryModel> entries) {
	 * this.userId = userId; //this.address = address; this.entries = entries; }
	 */

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
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

	public String getDueAmount() {
		return dueAmount;
	}

	public void setDueAmount(String dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AddressModel getAddress() {
		return address;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}
}
