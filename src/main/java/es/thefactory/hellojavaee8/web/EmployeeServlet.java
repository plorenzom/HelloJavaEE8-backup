package es.thefactory.hellojavaee8.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.ShortConverter;

import es.thefactory.hellojavaee8.biz.domain.EmployeeDto;
import es.thefactory.hellojavaee8.biz.service.EmployeeBeanLocal;
import es.thefactory.hellojavaee8.dal.DataAccessException;
import es.thefactory.hellojavaee8.util.web.WebActions;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(EmployeeServlet.class.getName());
    
    /**
     * 
     */
    @EJB(lookup = "java:module/EmployeeBean")
    private EmployeeBeanLocal employeeBean;
    
    /**
     * 
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("Entering method doDelete(request, response)");
        
        try {
            Short employeeId = Short.valueOf(request.getParameter("employeeId"));
            employeeBean.deleteById(employeeId);
            String url = request.getContextPath() + request.getServletPath() + "?action=" + WebActions.LIST;
            response.sendRedirect(url);
        } catch (NumberFormatException nfe) {
            LOGGER.log(Level.SEVERE, nfe.getMessage(), nfe);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (DataAccessException dae) {
            LOGGER.log(Level.SEVERE, dae.getMessage(), dae);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
        
        LOGGER.info("Exiting method doDelete(request, response)");
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Entering method doEdit(request, response)");
        
        try {
            Short employeeId = Short.valueOf(request.getParameter("employeeId"));
            EmployeeDto employeeDto = employeeBean.getById(employeeId);
            
            if (employeeDto != null) {
                request.setAttribute("employeeDto", employeeDto);
                String url = "/WEB-INF/employee/form.jsp";
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
                requestDispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (NumberFormatException nfe) {
            LOGGER.log(Level.SEVERE, nfe.getMessage(), nfe);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        LOGGER.info("Exiting method doEdit(request, response)");
    }
    
    /**
     * 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("Entering method doGet(request, response)");
        String action = request.getParameter("action");
        
        if (WebActions.DELETE.equals(action)) {
            this.doDelete(request, response);
        } else if (WebActions.EDIT.equals(action)) {
            this.doEdit(request, response);
        } else if (WebActions.NEW.equals(action)) {
            this.doNew(request, response);
        } else if (WebActions.LIST.equals(action)) {
            this.doList(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        
        LOGGER.info("Exiting method doGet(request, response)");
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Entering method doList(request, response)");
        List<EmployeeDto> employeeDtoList = employeeBean.getAll();
        request.setAttribute("employeeDtoList", employeeDtoList);
        String url = "/WEB-INF/employee/list.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        LOGGER.info("Exiting method doList(request, response)");
    }
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    private void doNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Entering method doNew(request, response)");
        String url = "/WEB-INF/employee/form.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        LOGGER.info("Exiting method doNew(request, response)");
    }
    
    /**
     * 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("Entering method doPost(request, response)");
        
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            
            // Register a custom converter for the specified destination class.
            ConvertUtils.register(new ShortConverter(null), Short.class);
            
            // Populate the properties of the specified bean from the request parameters.
            BeanUtils.populate(employeeDto, request.getParameterMap());
            
            employeeBean.save(employeeDto);
            String url = request.getContextPath() + request.getServletPath() + "?action=" + WebActions.LIST;
            response.sendRedirect(url);
        } catch (IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        
        LOGGER.info("Exiting method doPost(request, response)");
    }
}
