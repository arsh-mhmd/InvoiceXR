package invoice.xr.service;
import invoice.xr.dao.RegisterDao;
import invoice.xr.model.AddressModel;
import invoice.xr.model.ClientUser;
import invoice.xr.model.InvoiceModel;
import invoice.xr.model.OrderEntryModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.Address;
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
}
