package es.thefactory.hellojavaee8.dal.repository;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import es.thefactory.hellojavaee8.dal.DataAccessException;
import es.thefactory.hellojavaee8.dal.entity.Employee;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@ApplicationScoped
public class EmployeeRepository {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeRepository.class.getName());
    
    /**
     * 
     */
    @PersistenceContext
    private EntityManager entityManager;
    
    /**
     * 
     * @param employeeId
     * @throws DataAccessException
     */
    public void deleteById(Short employeeId) throws DataAccessException {
        LOGGER.info("Entering method deleteById(employeeId)");
        
        try {
            Employee employee = entityManager.find(Employee.class, employeeId);
            entityManager.remove(employee);
        } catch (IllegalArgumentException iae) {
            LOGGER.log(Level.SEVERE, iae.getMessage(), iae);
            throw new DataAccessException("Table row not deleted", iae);
        }
        
        LOGGER.info("Exiting method deleteById(employeeId)");
    }
    
    /**
     * 
     * @return List<Employee>
     */
    public List<Employee> getAll() {
        LOGGER.info("Entering method getAll()");
        TypedQuery<Employee> query = entityManager.createNamedQuery("Employee.findAll", Employee.class);
        List<Employee> employeeList = query.getResultList();
        LOGGER.info("Exiting method getAll()");
        
        return employeeList;
    }
    
    /**
     * 
     * @param employeeId
     * @return Employee
     */
    public Employee getById(Short employeeId) {
        LOGGER.info("Entering method getById(employeeId)");
        Employee employee = entityManager.find(Employee.class, employeeId);
        LOGGER.info("Exiting method getById(employeeId)");
        
        return employee;
    }
    
    /**
     * 
     * @param employee
     */
    public void insert(Employee employee) {
        LOGGER.info("Entering method insert(employee)");
        entityManager.persist(employee);
        LOGGER.info("Exiting method insert(employee)");
    }
    
    /**
     * 
     * @param employee
     */
    public void update(Employee employee) {
        LOGGER.info("Entering method update(employee)");
        entityManager.merge(employee);
        LOGGER.info("Exiting method update(employee)");
    }
}
