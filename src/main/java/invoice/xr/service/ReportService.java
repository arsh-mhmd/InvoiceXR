package invoice.xr.service;

import invoice.xr.dao.InvoiceDao;
import invoice.xr.model.InvoiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.List;

/**
 * This service is for requirement 5,let owner create reports
 * Author by Jay
 */

@Service
public class ReportService {
    @Autowired
    private InvoiceDao invoiceDao;

    public List<InvoiceModel> createReportByDate(Date date){
        return invoiceDao.getReportByDate(date);
    }

    public List<InvoiceModel> createReportByStatus(String status){
        return invoiceDao.getReportByStatus(status);
    }

    public List<InvoiceModel> createReportByClientId(String id){
        return invoiceDao.getReportByClientId(id);
    }


}
