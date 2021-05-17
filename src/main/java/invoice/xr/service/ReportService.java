package invoice.xr.service;

import invoice.xr.dao.InvoiceDao;
import invoice.xr.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private InvoiceDao invoiceDao;

    public List<InvoiceModel> createReportByDate(Date date,Date sDate){
        return invoiceDao.getReportByDate(date,sDate);
    }

    public List<InvoiceModel> createReportByStatus(String status){
        return invoiceDao.getReportByStatus(status);
    }

    public List<InvoiceModel> createReportByClientId(String id){
        return invoiceDao.getReportByClientId(id);
    }
}
