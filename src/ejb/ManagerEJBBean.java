package ejb;

import model.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class ManagerEJBBean implements ManagerEJBBeanLocal {

    @PersistenceContext(name = "persistence/dilers", unitName= "DilersPersistenceUnit")
    private EntityManager emD;

    @PersistenceContext(name= "persistence/users", unitName = "UsersPersistenceUnit")
    private EntityManager emU;

    @PersistenceContext(name = "persistence/autosalons", unitName = "AutosalonsPersistenceUnit")
    private EntityManager emA;

    @Resource
    private SessionContext sessionContext;

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

    /**
     * Распределенная транзакция - покупка автомобилей у дилера для автосалона
     * (Читать описания методов, которые вызываются внутри этого метода)
     * @param userOnline - менеджер онлайн
     * @param listOfCars - список автомобилей
     * @return
     */
    @Override
    public boolean makeOrderForSupply(User userOnline, List<Carforsupply> listOfCars){
        Autosalon autosalon = findAutosalonByManager(userOnline);
        if (autosalon.getAutosalonbills().size() != 0){
            Autosalonbill autoBill = autosalon.getAutosalonbills().get(0);
            int finalPrice = calculateFinalPriceOfCars(listOfCars);
            Supply supply = createNewSupply(finalPrice);
            for (Carforsupply car : listOfCars){
                getCarsFromDealer(car, supply);
                addCarsToAutosalon(car, autosalon);
            }
            Date dateOfTrade = new Date();
            Timestamp timestampOfTrade = new Timestamp(dateOfTrade.getTime());
            Autosalonbill bill = new Autosalonbill(timestampOfTrade, autoBill.getMoney() - finalPrice, autosalon);
            emA.persist(bill);
            emA.remove(autoBill);
            if (autoBill.getMoney() < finalPrice)
                sessionContext.setRollbackOnly();
        }
        if (sessionContext.getRollbackOnly())
            return false;
        return true;
    }

    private void addCarsToAutosalon(Carforsupply carforsupply, Autosalon autosalon){
        Car car = new Car(carforsupply.getColor(), carforsupply.getCost(),
                carforsupply.getCount(), carforsupply.getModel().getId(), autosalon);
        emA.persist(car);
    }


    /**
     * Создать новую поставку для дилера
     * @param summaryCost - итоговая сумма за поставку
     * @return поставка
     */
    private Supply createNewSupply(int summaryCost){
        Date dateOfSupply = new Date();
        Timestamp timestampOfSupply = new Timestamp(dateOfSupply.getTime());
        Supply supply = new Supply(timestampOfSupply, summaryCost );
        supply = emD.merge(supply);
        return supply;
    }


    /**
     * Вычесть автомобили с дилера, создать сделку
     * @param car - автомобиль
     */
    private void getCarsFromDealer(Carforsupply car, Supply supply){
        Carforsupply findCar = null;
        CriteriaQuery<Carforsupply> criteriaQuery = emD.getCriteriaBuilder().createQuery(Carforsupply.class);
        Root carRoot = criteriaQuery.from(Carforsupply.class);
        Predicate predicate1 = carRoot.get("color").in(car.getColor());
        //Predicate predicate2 = carRoot.get("model_id").in(car.getModel().getId());
        Predicate predicate3 = carRoot.get("cost").in(car.getCost());
        criteriaQuery.select(carRoot).where(predicate1);
        //criteriaQuery.select(carRoot).where(predicate2);
        criteriaQuery.select(carRoot).where(predicate3);
        List<Carforsupply> listOfResult = emD.createQuery(criteriaQuery).getResultList();
        findCar = listOfResult.get(0);
        int newCount = findCar.getCount() - car.getCount();
        findCar.setCount(newCount);
        emD.merge(findCar);
        Trade trade = new Trade(car.getCount(), supply, findCar);
        emD.persist(trade);
    }

    /**
     * Рассчитать итоговую сумму для автомобилей
     * @param cars - список автомобилей
     * @return сумма
     */
    @Override
    public int calculateFinalPriceOfCars(List<Carforsupply> cars){
        int finalPrice = 0;
        for (Carforsupply car : cars){
            finalPrice += car.getCost() * car.getCount();
        }
        return finalPrice;
    }

    /**
     * Найти автосалон по менеджеру
     * @param user - пользователь, находящийся онлайн в системе
     * @return - автосалон, в котором работает менеджер
     */
    @Override
    public Autosalon findAutosalonByManager(User user){
        int autosalonId = user.getAutosalon_id();
        Autosalon autosalon = emA.find(Autosalon.class, autosalonId);
        return autosalon;
    }

}
