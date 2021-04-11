package invoice.xr.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;

public class InvoiceModel {

	@Column(name = "invoiceNo")
	private String invoiceNo;
	
	@Column(name = "invoiceDate")
	private Date invoiceDate;
	
	@Column(name = "dueDate")
	private Date dueDate;
	
	@Column(name = "userId")
    private String userId;
	
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
	
    private AddressModel address;
    private List<OrderEntryModel> entries;

    public InvoiceModel(String userId, AddressModel address, List<OrderEntryModel> entries) {
        this.userId = userId;
        this.address = address;
        this.entries = entries;
    }

    public String getUserId() {
        return userId;
    }

    public Double getTotalPrice() {
        return getEntries().stream().mapToDouble(OrderEntryModel::getPriceTotal).sum();
    }

    public Integer getTotalQuantity() {
        return getEntries().stream().mapToInt(OrderEntryModel::getQuantity).sum();
    }

    public AddressModel getAddress() {
        return address;
    }

    public List<OrderEntryModel> getEntries() {
        return entries;
    }

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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setAddress(AddressModel address) {
		this.address = address;
	}

	public void setEntries(List<OrderEntryModel> entries) {
		this.entries = entries;
	}
}
