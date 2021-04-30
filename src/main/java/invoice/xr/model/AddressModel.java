package invoice.xr.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/** 
 * Model to store address of invoice. id is the foreign key of InvoiceModel table.
 * 
 * order_id field is the foreign key to OrderEntryModel.
 * 
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name = "AddressModel")
public class AddressModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "order_id")
	List<OrderEntryModel> entries;
	
	private Double salesTax;
	
	public Double getTotalPrice() {
		return getEntries().stream().mapToDouble(OrderEntryModel::getPriceTotal).sum();
	}

	public Integer getTotalQuantity() {
		return getEntries().stream().mapToInt(OrderEntryModel::getQuantity).sum();
	}

	public Double getGrandTotal() {
		return (getTotalPrice() * salesTax) / 100;
	}
	
	public Double getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(Double salesTax) {
		this.salesTax = salesTax;
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
	
	public List<OrderEntryModel> getEntries() {
		return entries;
	}

	public void setEntries(List<OrderEntryModel> entries) {
		this.entries = entries;
	}  
    
	public int getEntriesSize() {
		return entries.size();
	}
}
