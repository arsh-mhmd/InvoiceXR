package invoice.xr.dao;




import invoice.xr.model.TimerModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Jue Wang
 */
@Repository
@Transactional
public interface TimerDao extends CrudRepository<TimerModel, String> {
    public List<TimerModel> findAll();
    public List<TimerModel> findByType(int type);
    @Override
    public TimerModel save(TimerModel timerModel);
}
