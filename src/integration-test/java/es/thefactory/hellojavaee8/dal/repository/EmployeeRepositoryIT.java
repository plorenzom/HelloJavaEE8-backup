package es.thefactory.hellojavaee8.dal.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.thefactory.hellojavaee8.dal.DataAccessException;
import es.thefactory.hellojavaee8.dal.entity.Employee;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@RunWith(Arquillian.class)
public class EmployeeRepositoryIT {
    
    /**
     * 
     */
    private static final Short EMPLOYEE_ID = 1;
    
    /**
     * 
     */
    private static final String NAME = "James Arthur";
    
    /**
     * 
     */
    private static final String LAST_NAME = "Gosling";
    
    /**
     * 
     */
    private static final String POSITION = "Ingeniero Software";
    
    /**
     * 
     */
    private static final int GROSS_SALARY = 60000;
    
    /**
     * 
     */
    @Inject
    private EmployeeRepository employeeRepo;
    
    /**
     * 
     * @return WebArchive
     */
    @Deployment
    public static WebArchive createDeployment() {
        File assertjCoreLib = Maven.resolver()
            .loadPomFromFile("pom.xml")
            .resolve("org.assertj:assertj-core")
            .withoutTransitivity()
            .asSingleFile();
        WebArchive warFile = ShrinkWrap.create(WebArchive.class, "hello-javaee8-test.war");
        warFile.addPackages(true, "es/thefactory/hellojavaee8/dal")
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("scripts/hellojavaee8-test-db.sql")
            .addAsResource("scripts/hellojavaee8-test-data.sql")
            .addAsLibrary(assertjCoreLib);
        
        return warFile;
    }
    
    /**
     * 
     * @throws DataAccessException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteByIdShouldRemoveDataRow() throws DataAccessException {
        employeeRepo.deleteById(EmployeeRepositoryIT.EMPLOYEE_ID);
        
        Employee employee = employeeRepo.getById(EmployeeRepositoryIT.EMPLOYEE_ID);
        assertNull(employee);
    }
    
    /**
     * 
     * @throws DataAccessException
     */
    @Test(expected = DataAccessException.class)
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteByIdShouldThrowDataAccessException() throws DataAccessException {
        final Short EMPLOYEE_ID = 2;
        employeeRepo.deleteById(EMPLOYEE_ID);
    }
    
    /**
     * 
     * @throws DataAccessException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void getAllShouldReturnEmptyList() throws DataAccessException {
        employeeRepo.deleteById(EmployeeRepositoryIT.EMPLOYEE_ID);
        
        List<Employee> employeeList = employeeRepo.getAll();
        assertTrue(employeeList.isEmpty());
    }
    
    /**
     * 
     */
    @Test
    public void getAllShouldReturnNotEmptyList() {
        List<Employee> employeeList = employeeRepo.getAll();
        assertEquals(1, employeeList.size());
        assertNotNull(employeeList.get(0));
    }
    
    /**
     * 
     */
    @Test
    public void getByIdShouldReturnDataRow() {
        Employee employee = employeeRepo.getById(EmployeeRepositoryIT.EMPLOYEE_ID);
        assertNotNull(employee);
    }
    
    /**
     * 
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void insertShouldAddDataRow() {
        Employee testEmployee = new Employee();
        testEmployee.setName(EmployeeRepositoryIT.NAME);
        testEmployee.setLastName(EmployeeRepositoryIT.LAST_NAME);
        testEmployee.setPosition(EmployeeRepositoryIT.POSITION);
        testEmployee.setGrossSalary(EmployeeRepositoryIT.GROSS_SALARY);
        employeeRepo.insert(testEmployee);
        
        List<Employee> employeeList = employeeRepo.getAll();
        assertEquals(2, employeeList.size());
        
        Short employeeId = testEmployee.getEmployeeId();
        Employee employee = employeeRepo.getById(employeeId);
        assertThat(employee).usingRecursiveComparison().isEqualTo(testEmployee);
    }
    
    /**
     * 
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void updateShouldUpdateDataRow() {
        Employee testEmployee = new Employee();
        testEmployee.setEmployeeId(EmployeeRepositoryIT.EMPLOYEE_ID);
        testEmployee.setName(EmployeeRepositoryIT.NAME);
        testEmployee.setLastName(EmployeeRepositoryIT.LAST_NAME);
        testEmployee.setPosition(EmployeeRepositoryIT.POSITION);
        testEmployee.setGrossSalary(EmployeeRepositoryIT.GROSS_SALARY);
        employeeRepo.update(testEmployee);
        
        Employee employee = employeeRepo.getById(EmployeeRepositoryIT.EMPLOYEE_ID);
        assertThat(employee).usingRecursiveComparison().isEqualTo(testEmployee);
    }
}
