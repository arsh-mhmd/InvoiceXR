package invoice.xr.service;

import invoice.xr.dao.PaymentRecordDao;
import invoice.xr.model.InvoiceModel;
import invoice.xr.model.PaymentRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jue Wang
 */
@Service
public class PaymentRecordService {
    @Autowired
    PaymentRecordDao paymentRecordDao;

    @Autowired SendEmailService sendEmailService;

    public void addPaymentRecord(InvoiceModel invoiceModel,double paid,double due){
        String clientId = invoiceModel.getClientId();
        PaymentRecordModel paymentRecordModel = new PaymentRecordModel();
        paymentRecordModel.setClientId(clientId);
        paymentRecordModel.setInvoiceId(invoiceModel.getInvoiceNo());
        paymentRecordModel.setPaid(paid);
        paymentRecordDao.save(paymentRecordModel);
        sendEmailService.sendRecipe(invoiceModel,paymentRecordModel);

    }

    public List<PaymentRecordModel> getAllPaymentList(){
        return paymentRecordDao.findAllPayRecord();
    }
}
