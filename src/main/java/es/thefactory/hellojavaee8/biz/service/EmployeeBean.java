package es.thefactory.hellojavaee8.biz.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.inject.Inject;

import es.thefactory.hellojavaee8.biz.domain.EmployeeDto;
import es.thefactory.hellojavaee8.dal.DataAccessException;
import es.thefactory.hellojavaee8.dal.entity.Employee;
import es.thefactory.hellojavaee8.dal.repository.EmployeeRepository;
import es.thefactory.hellojavaee8.util.mapper.EmployeeMapper;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Stateless
public class EmployeeBean implements EmployeeBeanLocal {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeBean.class.getName());
    
    /**
     * 
     */
    @Inject
    private EmployeeMapper employeeMapper;
    
    /**
     * 
     */
    @Inject
    private EmployeeRepository employeeRepo;
    
    /**
     * 
     */
    @Override
    public void deleteById(Short employeeId) throws DataAccessException {
        LOGGER.info("Entering method deleteById(employeeId)");
        employeeRepo.deleteById(employeeId);
        LOGGER.info("Exiting method deleteById(employeeId)");
    }
    
    /**
     * 
     */
    @Override
    public List<EmployeeDto> getAll() {
        LOGGER.info("Entering method getAll()");
        List<Employee> employeeList = employeeRepo.getAll();
        List<EmployeeDto> employeeDtoList = employeeMapper.toDtoList(employeeList);
        LOGGER.info("Exiting method getAll()");
        
        return employeeDtoList;
    }
    
    /**
     * 
     */
    @Override
    public EmployeeDto getById(Short employeeId) {
        LOGGER.info("Entering method getById(employeeId)");
        Employee employee = employeeRepo.getById(employeeId);
        EmployeeDto employeeDto = employeeMapper.toDto(employee);
        LOGGER.info("Exiting method getById(employeeId)");
        
        return employeeDto;
    }
    
    /**
     * 
     */
    @Override
    public void save(EmployeeDto employeeDto) {
        LOGGER.info("Entering method save(employeeDto)");
        Employee employee = employeeMapper.toEntity(employeeDto);
        
        if (employee.getEmployeeId() == null) {
            employeeRepo.insert(employee);
            employeeDto.setEmployeeId(employee.getEmployeeId());
        } else {
            employeeRepo.update(employee);
        }
        
        LOGGER.info("Exiting method save(employeeDto)");
    }
}
