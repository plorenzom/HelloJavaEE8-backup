package es.thefactory.hellojavaee8.biz.service;

import java.util.List;

import javax.ejb.Local;

import es.thefactory.hellojavaee8.biz.domain.EmployeeDto;
import es.thefactory.hellojavaee8.dal.DataAccessException;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Local
public interface EmployeeBeanLocal {
    
    /**
     * 
     * @param employeeId
     * @throws DataAccessException
     */
    void deleteById(Short employeeId) throws DataAccessException;
    
    /**
     * 
     * @return List<EmployeeDto>
     */
    List<EmployeeDto> getAll();
    
    /**
     * 
     * @param employeeId
     * @return
     */
    EmployeeDto getById(Short employeeId);
    
    /**
     * 
     * @param employeeDto
     */
    void save(EmployeeDto employeeDto);
}
