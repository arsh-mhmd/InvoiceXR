package invoice.xr.dao;



import invoice.xr.model.ClientUser;
import invoice.xr.model.PaymentRecordModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Jue Wang
 */
public interface PaymentRecordDao extends Repository<PaymentRecordModel, Integer>{


    void save(PaymentRecordModel addressModel);

    @Query("SELECT paymentRecordModel from PaymentRecordModel paymentRecordModel")
    @Transactional(readOnly = true)
    List<PaymentRecordModel> findAllPayRecord();

}
