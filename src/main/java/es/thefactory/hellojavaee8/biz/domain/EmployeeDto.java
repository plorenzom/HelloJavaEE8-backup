package es.thefactory.hellojavaee8.biz.domain;

import java.io.Serializable;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
public class EmployeeDto implements Serializable {
    
    /**
     * 
     */
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
