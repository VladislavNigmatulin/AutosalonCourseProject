package ejb;

import model.Model;
import model.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Stateless
public class CommonEJBBean implements CommonEJBBeanLocal {

    public CommonEJBBean(){}

    @PersistenceContext(name = "persistence/users", unitName= "UsersPersistenceUnit")
    private EntityManager emU;

    @PersistenceContext(name = "persistence/dilers", unitName= "DilersPersistenceUnit")
    private EntityManager emD;

    @PersistenceContext(name = "persistence/autosalons", unitName = "AutosalonsPersistenceUnit")
    private EntityManager emA;

    /**
     * Поиск роли по названию
     * @return - роль
     */
    @Override
    public Role getRoleByTitle(String roleTitle){
        CriteriaQuery<Role> criteriaQuery = emU.getCriteriaBuilder().createQuery(Role.class);
        Root roleRoot = criteriaQuery.from(Role.class);
        Predicate predicate1 = roleRoot.get("title").in(roleTitle);
        criteriaQuery.select(roleRoot).where(predicate1);
        Role clientRole = emU.createQuery(criteriaQuery).getSingleResult();
        return clientRole;
    }

    /**
     * Найти модель по id
     * @param modelId - id модели
     * @return модель
     */
    @Override
    public Model findModelById(int modelId){
        Model model = emD.find(Model.class, modelId);
        return model;
    }

}
