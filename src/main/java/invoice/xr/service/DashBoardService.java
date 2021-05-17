package invoice.xr.service;


import invoice.xr.dao.InvoiceDao;
import invoice.xr.model.DashBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashBoardService {
    @Autowired
    InvoiceDao invoiceDao;


    public DashBoard getDashBoard(){
        DashBoard dashBoard=new DashBoard();
        try {
            dashBoard.setAmountMoney(invoiceDao.getAmountMoney());
        }catch (Exception e){
            dashBoard.setAmountMoney(0);
        }
        try {
            dashBoard.setPaidMoney(invoiceDao.getPaidMoney());
        }catch (Exception e){
            dashBoard.setPaidMoney(0);
        }
        try {
            dashBoard.setUnpaidMoney(invoiceDao.getUnpaidMoney());
        }catch (Exception e){
            dashBoard.setUnpaidMoney(0);
        }
        dashBoard.setAmountInvoices(invoiceDao.getAmountInvoices());
        dashBoard.setUnpaidInvoices(invoiceDao.getUnpaidInvoices());
        dashBoard.setHalfPaidInvoices(invoiceDao.getHalfPaidInvoices());
        dashBoard.setPaidInvoices(invoiceDao.getPaidInvoices());
        return  dashBoard;
    }

}
