package es.thefactory.hellojavaee8.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@WebServlet("/helloworld")
public class HelloWorldServlet extends HttpServlet {
    
    /**
     * 
     */
    private static final Logger LOGGER = Logger.getLogger(HelloWorldServlet.class.getName());
    
    /**
     * 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("Entering method doGet(request, response)");
        String url = "/WEB-INF/helloworld/helloworld.jsp";
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
        requestDispatcher.forward(request, response);
        LOGGER.info("Exiting method doGet(request, response)");
    }
    
    /**
     * 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        LOGGER.info("Entering method doPost(request, response)");
        this.doGet(request, response);
        LOGGER.info("Exiting method doPost(request, response)");
    }
}
