package es.thefactory.hellojavaee8.biz.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.thefactory.hellojavaee8.biz.domain.EmployeeDto;
import es.thefactory.hellojavaee8.dal.DataAccessException;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@RunWith(Arquillian.class)
public class EmployeeBeanIT {
    
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
    @EJB(lookup = "java:module/EmployeeBean")
    private EmployeeBeanLocal employeeBean;
    
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
        warFile.addPackages(true, Filters.exclude(".*Servlet.class$"), "es.thefactory.hellojavaee8")
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
        employeeBean.deleteById(EmployeeBeanIT.EMPLOYEE_ID);
        
        EmployeeDto employeeDto = employeeBean.getById(EmployeeBeanIT.EMPLOYEE_ID);
        assertNull(employeeDto);
    }
    
    /**
     * 
     * @throws DataAccessException
     */
    @Test(expected = DataAccessException.class)
    @Transactional(value = TransactionMode.ROLLBACK)
    public void deleteByIdShouldThrowDataAccessException() throws DataAccessException {
        final Short EMPLOYEE_ID = 2;
        employeeBean.deleteById(EMPLOYEE_ID);
    }
    
    /**
     * 
     * @throws DataAccessException
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void getAllShouldReturnEmptyList() throws DataAccessException {
        employeeBean.deleteById(EmployeeBeanIT.EMPLOYEE_ID);
        
        List<EmployeeDto> employeeDtoList = employeeBean.getAll();
        assertTrue(employeeDtoList.isEmpty());
    }
    
    /**
     * 
     */
    @Test
    public void getAllShouldReturnNotEmptyList() {
        List<EmployeeDto> employeeDtoList = employeeBean.getAll();
        assertEquals(1, employeeDtoList.size());
        assertNotNull(employeeDtoList.get(0));
    }
    
    /**
     * 
     */
    @Test
    public void getByIdShouldReturnDataRow() {
        EmployeeDto employeeDto = employeeBean.getById(EmployeeBeanIT.EMPLOYEE_ID);
        assertNotNull(employeeDto);
    }
    
    /**
     * 
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void saveShouldAddDataRow() {
        EmployeeDto testEmployeeDto = new EmployeeDto();
        testEmployeeDto.setName(EmployeeBeanIT.NAME);
        testEmployeeDto.setLastName(EmployeeBeanIT.LAST_NAME);
        testEmployeeDto.setPosition(EmployeeBeanIT.POSITION);
        testEmployeeDto.setGrossSalary(EmployeeBeanIT.GROSS_SALARY);
        employeeBean.save(testEmployeeDto);
        
        List<EmployeeDto> employeeDtoList = employeeBean.getAll();
        assertEquals(2, employeeDtoList.size());
        
        Short employeeId = testEmployeeDto.getEmployeeId();
        EmployeeDto employeeDto = employeeBean.getById(employeeId);
        assertThat(employeeDto).usingRecursiveComparison().isEqualTo(testEmployeeDto);
    }
    
    /**
     * 
     */
    @Test
    @Transactional(value = TransactionMode.ROLLBACK)
    public void saveShouldUpdateDataRow() {
        EmployeeDto testEmployeeDto = new EmployeeDto();
        testEmployeeDto.setEmployeeId(EmployeeBeanIT.EMPLOYEE_ID);
        testEmployeeDto.setName(EmployeeBeanIT.NAME);
        testEmployeeDto.setLastName(EmployeeBeanIT.LAST_NAME);
        testEmployeeDto.setPosition(EmployeeBeanIT.POSITION);
        testEmployeeDto.setGrossSalary(EmployeeBeanIT.GROSS_SALARY);
        employeeBean.save(testEmployeeDto);
        
        EmployeeDto employeeDto = employeeBean.getById(EmployeeBeanIT.EMPLOYEE_ID);
        assertThat(employeeDto).usingRecursiveComparison().isEqualTo(testEmployeeDto);
    }
}
