package es.thefactory.hellojavaee8.dal;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
public class DataAccessException extends Exception {
    
    /**
     * 
     * @param message
     */
    public DataAccessException(String message) {
        super(message);
    }
    
    /**
     * 
     * @param cause
     */
    public DataAccessException(Throwable cause) {
        super(cause);
    }
    
    /**
     * 
     * @param message
     * @param cause
     */
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
