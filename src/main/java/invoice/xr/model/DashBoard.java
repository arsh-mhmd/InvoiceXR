package invoice.xr.model;




import javax.persistence.*;


public class DashBoard{


    private double amountMoney;

    private double paidMoney;

    private double unpaidMoney;

    private int amountInvoices;

    private int unpaidInvoices;

    private int halfPaidInvoices;

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

    @Override
    public String toString() {
        return  amountMoney +
                "," + paidMoney +
                "," + unpaidMoney +
                "," + amountInvoices +
                "," + unpaidInvoices +
                "," + halfPaidInvoices +
                "," + paidInvoices;
    }
}
