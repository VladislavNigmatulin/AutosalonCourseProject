package ejb;

import model.*;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Stateless
public class ClientEJBBean implements ClientEJBBeanLocal {

    @PersistenceContext(name= "persistence/users", unitName = "UsersPersistenceUnit")
    private EntityManager emU;

    @PersistenceContext(name = "persistence/autosalons", unitName = "AutosalonsPersistenceUnit")
    private EntityManager emA;

    @Resource
    private SessionContext sessionContext;

    /**
     * Получить список автомобилей
     * @return список автомобилей
     */
    @Override
    public List<Car> getListOfCars(){
        Query query = emA.createQuery("SELECT c from Car c WHERE c.count > 0");
        List<Car> listOfCars = query.getResultList();
        return listOfCars;
    }

    /**
     * Заказ на покупку автомобиля
     * @param userOnline - - клиент, совершающий заказ
     * @param selectedCar - автомобиль
     * @return
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean makeOrder(User userOnline, Car selectedCar){
        Autosalon autosalon = selectedCar.getAutosalon();
        if (autosalon.getAutosalonbills().size() != 0){
            Autosalonbill autoBill = autosalon.getAutosalonbills().get(0);
            Bill clientBill = userOnline.getBill();
            Car newCar = minusCarFromAutosalon(selectedCar, clientBill.getCount());
            Orderforcar orderforcar = createOrderForCar(userOnline, newCar);
            addMoneyToAutosalonBill(autoBill, selectedCar.getCost());
            History history = createNewRecordInHistory(clientBill, orderforcar, selectedCar.getCost());
            int moneyOnClientBill = clientBill.getCount();
            if (moneyOnClientBill < selectedCar.getCost()){
                sessionContext.setRollbackOnly();
            } else {
                moneyOnClientBill = moneyOnClientBill - selectedCar.getCost();
                clientBill.setCount(moneyOnClientBill);
                Date today = new Date();
                Timestamp timestampToday = new Timestamp(today.getTime());
                clientBill.setDate(timestampToday);
                emU.merge(clientBill);
                history.setOrderForCar_ID(orderforcar.getId());
                emU.merge(history);
            }
        }
        if (sessionContext.getRollbackOnly())
            return false;
        return true;
    }

    /**
     * Создать запись в истории
     * @param bill - счет клиента
     * @param orderforcar - заказ на автомобиль
     * @param money - сумма
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private History createNewRecordInHistory(Bill bill, Orderforcar orderforcar, int money){
        History history = new History("Покупка автомобиля", money, orderforcar.getId(), bill);
        history = emU.merge(history);
        return history;
    }

    /**
     * Добавить деньги на счет автосалона
     * @param autosalonBill - счет автосалона
     * @param money - количество
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Autosalonbill addMoneyToAutosalonBill(Autosalonbill autosalonBill, int money){
        int count = autosalonBill.getMoney();
        autosalonBill.setMoney(count + money);
        Date today = new Date();
        Timestamp timestampToday = new Timestamp(today.getTime());
        autosalonBill.setDate(timestampToday);
        autosalonBill = emA.merge(autosalonBill);
        return autosalonBill;
    }


    /**
     * Создать запись в таблице заказы
     * @param userOnline - клиент, совершающий заказ
     * @param newCar - автомобиль, который покупает клиент
     * @return
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Orderforcar createOrderForCar(User userOnline, Car newCar){
        Date dateOfOrdering = new Date();
        Timestamp timestampOfOrdering = new Timestamp(dateOfOrdering.getTime());
        Orderforcar order = null;
        if (userOnline.getBill().getCount() < newCar.getCost())
            order = new Orderforcar(timestampOfOrdering, userOnline.getId(), newCar, 1);
        else {
            order = new Orderforcar(timestampOfOrdering, userOnline.getId(), newCar, 0);
        }
        order = emA.merge(order);
        emA.flush();
        return order;
    }

    /**
     * Вычесть автомобиль из автосалона
     * @param selectedCar - автомобиль
     * @return - автомобиль
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    private Car minusCarFromAutosalon(Car selectedCar, int money){
        selectedCar.setCount(selectedCar.getCount() - 1);
        Car newCar = emA.merge(selectedCar);
        if (money < selectedCar.getCost()){
            selectedCar.setCount(selectedCar.getCount() + 1);
            newCar = emA.merge(selectedCar);
        }
        return newCar;
    }


    /**
     * Получить список заказов
     * @param user - пользователь онлайн
     * @return список заказов
     */
    @Override
    public List<Orderforcar> getListOfOrderforcars(User user){
        Query query = emA.createQuery("SELECT o from Orderforcar o WHERE o.cancel = 0 AND o.user_id = :userId");
        query.setParameter("userId", user.getId());
        List<Orderforcar> listOfOrders = query.getResultList();
        return listOfOrders;
    }
}
