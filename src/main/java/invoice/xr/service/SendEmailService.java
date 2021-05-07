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

        String header ="Hi "+addressModel.getShippingFirstName()+","+addressModel.getShippingLastName()+". This is an invoice from InvoiceXR Inc.\n\n";
        String billing = "Bill to:"+addressModel.getBillingFirstName()+addressModel.getBillingLastName()+"  Company:"+addressModel.getBillingPostalCode()+
                " address:"+ addressModel.getBillingStreetName()+" "+addressModel.getBillingTown()+" "+addressModel.getBillingCountry()+" post code:"+
                addressModel.getBillingPostalCode()+"\n\n";
        String shipping = "Ship to:"+addressModel.getShippingFirstName()+addressModel.getShippingLastName()+"  Company:"+addressModel.getShippingPostalCode()+
                " address:"+ addressModel.getShippingStreetName()+" "+addressModel.getShippingTown()+" "+addressModel.getShippingCountry()+" post code:"+
                addressModel.getShippingPostalCode()+"\n\n";
        String date = "Invoice Date:"+invoiceModel.getInvoiceDate()+"    Due Date:"+invoiceModel.getDueDate()+"    Remaining Due:"+invoiceModel.getDueAmount()+"\n";

        double withDue = invoiceModel.getDueAmount()!=null? invoiceModel.getDueAmount() + invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal() : invoiceModel.getAddress().getTotalPrice() + invoiceModel.getAddress().getGrandTotal();
        String money = "Sale Tax:"+addressModel.getSalesTax()+" total grand:"+addressModel.getGrandTotal()+" total price:"+invoiceModel.getAddress().getTotalPrice()+" with due:"+withDue;
        String end = "Best Wishes,\n InvoiceXr Inc.";
        return header+billing+shipping+date+money+end;
    }

    public void sendInvoiceNow(String number){
        final InvoiceModel invoice = invoiceService.getInvoice(number);
        String clientId = invoice.getClientId();
        int id = Integer.parseInt(clientId);
        final ClientUser clientUser = registerDao.findClientByMainId(id);
        String Email = clientUser.getEmail();
        String content = this.getInvoiceData(invoice);
        //mailService.sendSimpleMail(Email,"2",content);
        System.out.println(content);
    }
}
