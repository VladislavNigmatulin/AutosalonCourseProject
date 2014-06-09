package ejb;

import model.Diler;
import model.Model;
import model.Role;
import model.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

@Stateless
public class ManagerEJBBean implements ManagerEJBBeanLocal {

    @PersistenceContext(name = "persistence/dilers", unitName= "DilersPersistenceUnit")
    private EntityManager emD;

    public ManagerEJBBean(){}

    /**
     * Получить список всех дилеров
     * @return список дилеров
     */
    @Override
    public List<Diler> getListOfDilers(){
        CriteriaQuery<Diler> criteriaQuery = emD.getCriteriaBuilder().createQuery(Diler.class);
        criteriaQuery.select(criteriaQuery.from(Diler.class));
        List<Diler> listOfDilers = emD.createQuery(criteriaQuery).getResultList();
        return listOfDilers;
    }

}
