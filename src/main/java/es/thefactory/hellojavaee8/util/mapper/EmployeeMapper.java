package es.thefactory.hellojavaee8.util.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import es.thefactory.hellojavaee8.biz.domain.EmployeeDto;
import es.thefactory.hellojavaee8.dal.entity.Employee;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Mapper(componentModel = "cdi")
public interface EmployeeMapper {
    
    /**
     * 
     * @param employee
     * @return EmployeeDto
     */
    EmployeeDto toDto(Employee employee);
    
    /**
     * 
     * @param employeeList
     * @return List<EmployeeDto>
     */
    List<EmployeeDto> toDtoList(List<Employee> employeeList);
    
    /**
     * 
     * @param employeeDto
     * @return Employee
     */
    Employee toEntity(EmployeeDto employeeDto);
}
