package invoice.xr.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * QuoteModel to create Quote.
 * 
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name = "QuoteModel")
public class QuoteModel {

	@Id
	private String id;

	@Column(name = "quoteNo")
	private String quoteNo;
	
	@Column(name = "clientName")
	private String clientName;
	
	@Column(name = "shippingClientName")
	private String shippingClientName;
	
	@Column(name = "quoteDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date quoteDate;
	
	@Column(name = "dueDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date dueDate;
	
	@Column(name = "shippingDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date shippingDate;
	
	@Column(name = "expDeliveryDate")
	@JsonFormat(pattern="dd-MM-yyyy")
	private Date expDeliveryDate;

	@Column(name = "clientId")
	private String clientId;
	
	@Column(name = "shippingClientId")
	private String shippingClientId;
	
	@Column(name = "status")
	private String status;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "quote_entry_id")
	List<QuoteEntryModel> quoteEntries;
	
	private Double salesTax;
	
	public Double getTotalPrice() {
		return getQuoteEntries().stream().mapToDouble(QuoteEntryModel::getPriceTotal).sum();
	}

	public Integer getTotalQuantity() {
		return getQuoteEntries().stream().mapToInt(QuoteEntryModel::getQuantity).sum();
	}

	public Double getGrandTotal() {
		return (getTotalPrice() * salesTax) / 100;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public Date getQuoteDate() {
		return quoteDate;
	}

	public void setQuoteDate(Date quoteDate) {
		this.quoteDate = quoteDate;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public List<QuoteEntryModel> getQuoteEntries() {
		return quoteEntries;
	}

	public void setQuoteEntries(List<QuoteEntryModel> quoteEntries) {
		this.quoteEntries = quoteEntries;
	}

	public Double getSalesTax() {
		return salesTax;
	}

	public void setSalesTax(Double salesTax) {
		this.salesTax = salesTax;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getShippingDate() {
		return shippingDate;
	}

	public void setShippingDate(Date shippingDate) {
		this.shippingDate = shippingDate;
	}

	public Date getExpDeliveryDate() {
		return expDeliveryDate;
	}

	public void setExpDeliveryDate(Date expDeliveryDate) {
		this.expDeliveryDate = expDeliveryDate;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getShippingClientName() {
		return shippingClientName;
	}

	public void setShippingClientName(String shippingClientName) {
		this.shippingClientName = shippingClientName;
	}

	public String getShippingClientId() {
		return shippingClientId;
	}

	public void setShippingClientId(String shippingClientId) {
		this.shippingClientId = shippingClientId;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	
	

}
