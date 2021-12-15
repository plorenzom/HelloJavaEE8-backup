package es.thefactory.hellojavaee8.dal.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@Entity
@Table(name = "Employee")
@NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e")
public class Employee implements Serializable {
    
    /**
     * 
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short employeeId;
    
    /**
     * 
     */
    private String name;
    
    /**
     * 
     */
    private String lastName;
    
    /**
     * 
     */
    private String position;
    
    /**
     * 
     */
    private int grossSalary;
    
    /**
     * 
     * @return Short
     */
    public Short getEmployeeId() {
        return employeeId;
    }
    
    /**
     * 
     * @param employeeId
     */
    public void setEmployeeId(Short employeeId) {
        this.employeeId = employeeId;
    }
    
    /**
     * 
     * @return String
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 
     * @return String
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * 
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * 
     * @return String
     */
    public String getPosition() {
        return position;
    }
    
    /**
     * 
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }
    
    /**
     * 
     * @return int
     */
    public int getGrossSalary() {
        return grossSalary;
    }
    
    /**
     * 
     * @param grossSalary
     */
    public void setGrossSalary(int grossSalary) {
        this.grossSalary = grossSalary;
    }
}
