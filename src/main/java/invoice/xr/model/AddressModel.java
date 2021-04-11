package invoice.xr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AddressModel")
public class AddressModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "billingFirstName")
    private String billingFirstName;
	
	@Column(name = "billingLastName")
    private String billingLastName;
	
	@Column(name = "billingStreetName")
    private String billingStreetName;
	
	@Column(name = "billingPostalCode")
    private String billingPostalCode;
	
	@Column(name = "billingTown")
    private String billingTown;
	
	@Column(name = "billingCountry")
    private String billingCountry;
	
	@Column(name = "shippingFirstName")
    private String shippingFirstName;
	
	@Column(name = "shippingLastName")
    private String shippingLastName;
	
	@Column(name = "shippingStreetName")
    private String shippingStreetName;
	
	@Column(name = "shippingPostalCode")
    private String shippingPostalCode;
	
	@Column(name = "shippingTown")
    private String shippingTown;
	
	@Column(name = "shippingCountry")
    private String shippingCountry;
	
	@Column(name = "invoiceNo")
    private String invoiceNo;

    public AddressModel(String billingFirstName, String billingLastName, String billingStreetName, String billingPostalCode, String billingTown, String billingCountry,
    		String shippingFirstName, String shippingLastName, String shippingStreetName, String shippingPostalCode, String shippingTown, String shippingCountry) {
        this.billingFirstName = billingFirstName;
        this.billingLastName = billingLastName;
        this.billingStreetName = billingStreetName;
        this.billingPostalCode = billingPostalCode;
        this.billingTown = billingTown;
        this.billingCountry = billingCountry;
        this.shippingFirstName = shippingFirstName;
        this.shippingLastName = shippingLastName;
        this.shippingStreetName = shippingStreetName;
        this.shippingPostalCode = shippingPostalCode;
        this.shippingTown = shippingTown;
        this.shippingCountry = shippingCountry;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBillingFirstName() {
		return billingFirstName;
	}

	public void setBillingFirstName(String billingFirstName) {
		this.billingFirstName = billingFirstName;
	}

	public String getBillingLastName() {
		return billingLastName;
	}

	public void setBillingLastName(String billingLastName) {
		this.billingLastName = billingLastName;
	}

	public String getBillingStreetName() {
		return billingStreetName;
	}

	public void setBillingStreetName(String billingStreetName) {
		this.billingStreetName = billingStreetName;
	}

	public String getBillingPostalCode() {
		return billingPostalCode;
	}

	public void setBillingPostalCode(String billingPostalCode) {
		this.billingPostalCode = billingPostalCode;
	}

	public String getBillingTown() {
		return billingTown;
	}

	public void setBillingTown(String billingTown) {
		this.billingTown = billingTown;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	public String getShippingFirstName() {
		return shippingFirstName;
	}

	public void setShippingFirstName(String shippingFirstName) {
		this.shippingFirstName = shippingFirstName;
	}

	public String getShippingLastName() {
		return shippingLastName;
	}

	public void setShippingLastName(String shippingLastName) {
		this.shippingLastName = shippingLastName;
	}

	public String getShippingStreetName() {
		return shippingStreetName;
	}

	public void setShippingStreetName(String shippingStreetName) {
		this.shippingStreetName = shippingStreetName;
	}

	public String getShippingPostalCode() {
		return shippingPostalCode;
	}

	public void setShippingPostalCode(String shippingPostalCode) {
		this.shippingPostalCode = shippingPostalCode;
	}

	public String getShippingTown() {
		return shippingTown;
	}

	public void setShippingTown(String shippingTown) {
		this.shippingTown = shippingTown;
	}

	public String getShippingCountry() {
		return shippingCountry;
	}

	public void setShippingCountry(String shippingCountry) {
		this.shippingCountry = shippingCountry;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	
    
    
}
