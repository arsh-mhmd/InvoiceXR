package invoice.xr.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Model which has order items of invoice. Related to AddressModel with id field.
 * @author Arshath Mohammed
 *
 */
@Entity
@Table(name = "OrderEntryModel")
public class OrderEntryModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "productName")
    private String productName;
	
	@Column(name = "quantity")
    private Integer quantity;
	
	@Column(name = "price")
    private Double price;
	
	@Column(name = "invoiceNo")
    private String invoiceNo;

    public OrderEntryModel(String productName, Integer quantity, Double price) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public OrderEntryModel() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return id;
	}

	public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    public Double getPriceTotal() {
        return quantity * price;
    }

	public void setId(Integer id) {
		this.id = id;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
    
}
