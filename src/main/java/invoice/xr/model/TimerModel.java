package invoice.xr.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Jue Wang
 */
@Entity
@Table(name="TimerModel")
public class TimerModel {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="invoiceId")
    private String invoiceId;

    /**
     * type = 0: monthly task
     * type = 1: due day task
     */
    @Column(name="type")
    private int type;

    @Column(name="dueDay")
    private int dueDay;

    @Column(name = "lastSentDay")
    @JsonFormat(pattern="dd-MM-yyyy")
    private Date lastSentDay;

    /**
     * sent = 1 or sent = 0
     */
    @Column(name = "sent")
    @JsonFormat(pattern="dd-MM-yyyy")
    private int sent;

    public Date getLastSentDay() {
        return lastSentDay;
    }

    public int getDueDay() {
        return dueDay;
    }

    public int getSent() {
        return sent;
    }

    public int getType() {
        return type;
    }

    public Integer getId() {
        return id;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDueDay(int dueDay) {
        this.dueDay = dueDay;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setLastSentDay(Date lastSentDay) {
        this.lastSentDay = lastSentDay;
    }

    public void setSent(int sent) {
        this.sent = sent;
    }
}
