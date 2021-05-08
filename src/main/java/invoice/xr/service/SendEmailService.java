package invoice.xr.service;
import invoice.xr.dao.RegisterDao;
import invoice.xr.dao.TimerDao;
import invoice.xr.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Jue Wang
 */
@Service
public class SendEmailService {
    @Autowired InvoiceService invoiceService;
    @Autowired
    RegisterDao registerDao;
    @Autowired MailService mailService;
    @Autowired
    TimerDao timerDao;

    public String getInvoiceData(InvoiceModel invoiceModel){
        AddressModel addressModel =invoiceModel.getAddress();
        List<OrderEntryModel> entries = addressModel.getEntries();

        String header ="Hi "+addressModel.getShippingFirstName()+","+addressModel.getShippingLastName()+"\nThis is an invoice from InvoiceXR Inc.\n\n";
        String billing = "Bill to:"+addressModel.getBillingFirstName()+addressModel.getBillingLastName()+"  Company:"+addressModel.getBillingPostalCode()+
                " address:"+ addressModel.getBillingStreetName()+" "+addressModel.getBillingTown()+" "+addressModel.getBillingCountry()+" post code:"+
                addressModel.getBillingPostalCode()+"\n\n";
        String shipping = "Ship to:"+addressModel.getShippingFirstName()+addressModel.getShippingLastName()+"  Company:"+addressModel.getShippingPostalCode()+
                " address:"+ addressModel.getShippingStreetName()+" "+addressModel.getShippingTown()+" "+addressModel.getShippingCountry()+" post code:"+
                addressModel.getShippingPostalCode()+"\n\n";
        String date = "Invoice Date:"+invoiceModel.getInvoiceDate()+"    Due Date:"+invoiceModel.getDueDate()+"    Remaining Due:"+invoiceModel.getDueAmount()+"\n";

        //double withDue = invoiceModel.getDueAmount()!=null? invoiceModel.getDueAmount() + invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal() : invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal();
        double withDue = 75;
        //String money = "Sale Tax:"+addressModel.getSalesTax()+" total grand:"+addressModel.getGrandTotal()+" total price:"+invoiceModel.getAddress().getTotalPrice()+" with due:"+withDue;
        String money = "Sale Tax:"+"4"+" total grand:65"+" total price:"+"874"+" with due:"+withDue+"\n\n";
        String end = "Best Wishes,\nInvoiceXr Inc.";
        return header+billing+shipping+date+money+end;
    }

    public void sendInvoiceNow(String number){
        final InvoiceModel invoice = invoiceService.getInvoice(number);
        String clientId = invoice.getClientId();
        final ClientUser clientUser = registerDao.findClientById(clientId);
        String Email = clientUser.getEmail();
        String content = this.getInvoiceData(invoice);
        mailService.sendSimpleMail(Email,"Please check your invoice. This invoice is from InvoiceXr inc.",content);
    }

    public TimerModel setTimerConfig(TimerModel timerModel){
        if (timerModel.getType()==0){
            timerModel.setDueDay(30);
        }
        timerModel.setSent(0);
        timerDao.save(timerModel);
        return timerModel;
    }

    public void sendMonthlyEmail() throws ParseException {
        List<TimerModel> timerTasks = timerDao.findByType(0);
        for(int i =0; i<timerTasks.size();i++){
            TimerModel timerTask = timerTasks.get(i);

            Date today = new Date();
            if(timerTask.getLastSentDay()==null || (today.getTime()-timerTask.getLastSentDay().getTime())/(1000 * 60 * 60 * 24) > 30){
                // send email
                sendInvoiceNow(timerTask.getInvoiceId());
                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                String dateString = format.format(today);
                Date date = format.parse(dateString);
                timerTask.setLastSentDay((java.sql.Date) date);
                timerDao.save(timerTask);
            }
        }
    }

    public void sendDueEmail() throws ParseException {
        List<TimerModel> timerTasks = timerDao.findByType(1);
        for(int i =0; i<timerTasks.size();i++){
            TimerModel timerTask = timerTasks.get(i);
            InvoiceModel invoiceModel = invoiceService.getInvoice(timerTask.getInvoiceId());
            Date today = new Date();
            if (timerTask.getSent()==0){
                if ((invoiceModel.getDueDate().getTime()-today.getTime())/(1000 * 60 * 60 * 24)<timerTask.getDueDay()){
                    sendInvoiceNow(timerTask.getInvoiceId());
                    SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                    String dateString = format.format(today);
                    Date date = format.parse(dateString);

                    timerTask.setLastSentDay((java.sql.Date) date);
                    timerTask.setSent(1);
                    timerDao.save(timerTask);
                }
            }

        }

    }
}
