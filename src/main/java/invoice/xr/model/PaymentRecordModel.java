package invoice.xr.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Jue Wang
 */
@Entity
@Table(name="PaymentRecordModel")
public class PaymentRecordModel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="invoiceId")
    private String invoiceId;

    @Column(name="clientId")
    private String clientId;

    @Column(name="paid")
    private Double paid;

    @CreatedDate
    @Column(name="createdDate")
    private Date createdDate;

    public PaymentRecordModel() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Double getPaid() {
        return paid;
    }

    public void setPaid(Double paid) {
        this.paid = paid;
    }


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
