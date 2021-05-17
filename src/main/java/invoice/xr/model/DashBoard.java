package invoice.xr.model;




import javax.persistence.*;

@Entity
@Table(name="DashBoard")
public class DashBoard{
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="amountMoney")
    private double amountMoney;

    @Column(name="paidMoney")
    private double paidMoney;

    @Column(name="unpaidMoney")
    private double unpaidMoney;

    @Column(name="amountInvoices")
    private int amountInvoices;

    @Column(name="unpaidInvoices")
    private int unpaidInvoices;

    @Column(name="halfPaidInvoices")
    private int halfPaidInvoices;

    @Column(name="paidInvoices")
    private int paidInvoices;

    public void setAmountMoney(double amountMoney) {
        this.amountMoney = amountMoney;
    }

    public void setPaidMoney(double paidMoney) {
        this.paidMoney = paidMoney;
    }

    public void setUnpaidMoney(double unpaidMoney) {
        this.unpaidMoney = unpaidMoney;
    }

    public void setAmountInvoices(int amountInvoices) {
        this.amountInvoices = amountInvoices;
    }

    public void setUnpaidInvoices(int unpaidInvoices) {
        this.unpaidInvoices = unpaidInvoices;
    }

    public void setHalfPaidInvoices(int halfPaidInvoices) {
        this.halfPaidInvoices = halfPaidInvoices;
    }

    public void setPaidInvoices(int paidInvoices) {
        this.paidInvoices = paidInvoices;
    }
}
