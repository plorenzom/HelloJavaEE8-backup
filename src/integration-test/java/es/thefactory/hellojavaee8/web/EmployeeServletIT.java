package es.thefactory.hellojavaee8.web;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.thefactory.hellojavaee8.util.web.URLBuilder;
import es.thefactory.hellojavaee8.util.web.WebActions;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
@RunAsClient
@RunWith(Arquillian.class)
public class EmployeeServletIT {
    
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
    private static final String URL_PATH = "/employee";
    
    /**
     * 
     */
    @ArquillianResource
    private URL baseURL;
    
    /**
     * 
     * @return WebArchive
     */
    @Deployment
    public static WebArchive createDeployment() {
        final String WEBAPP_ROOT = "src/main/webapp";
        File commonsBeanutilsLib = Maven.resolver()
            .loadPomFromFile("pom.xml")
            .resolve("commons-beanutils:commons-beanutils")
            .withoutTransitivity()
            .asSingleFile();
        WebArchive warFile = ShrinkWrap.create(WebArchive.class, "hello-javaee8-test.war");
        warFile.addPackages(true, "es.thefactory.hellojavaee8")
            .addAsWebResource(new File(WEBAPP_ROOT, "WEB-INF/employee/form.jsp"), "WEB-INF/employee/form.jsp")
            .addAsWebResource(new File(WEBAPP_ROOT, "WEB-INF/employee/list.jsp"), "WEB-INF/employee/list.jsp")
            .addAsResource("META-INF/persistence-test.xml", "META-INF/persistence.xml")
            .addAsResource("scripts/hellojavaee8-test-db.sql")
            .addAsResource("scripts/hellojavaee8-test-data.sql")
            .addAsLibrary(commonsBeanutilsLib);
        
        return warFile;
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doDeleteShouldReturnBadRequestStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.DELETE);
        params.put("employeeId", "");
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doDeleteShouldReturnNotFoundStatusCode() throws IOException {
        final Short EMPLOYEE_ID = 2;
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.DELETE);
        params.put("employeeId", String.valueOf(EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doEditShouldReturnBadRequestStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.EDIT);
        params.put("employeeId", "");
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doEditShouldReturnNotFoundStatusCode() throws IOException {
        final Short EMPLOYEE_ID = 2;
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.EDIT);
        params.put("employeeId", String.valueOf(EMPLOYEE_ID));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_NOT_FOUND, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doGetShouldReturnBadRequestStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", "");
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_BAD_REQUEST, responseCode);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doListShouldReturnOkStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.LIST);
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);
        
        String contentType = conn.getContentType();
        assertEquals("text/html;charset=ISO-8859-1", contentType);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doNewShouldReturnOkStatusCode() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("action", WebActions.NEW);
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_OK, responseCode);
        
        String contentType = conn.getContentType();
        assertEquals("text/html;charset=ISO-8859-1", contentType);
    }
    
    /**
     * 
     * @throws IOException
     */
    @Test
    public void doPostShouldRedirectToListView() throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("employeeId", String.valueOf(EmployeeServletIT.EMPLOYEE_ID));
        params.put("name", EmployeeServletIT.NAME);
        params.put("lastName", EmployeeServletIT.LAST_NAME);
        params.put("position", EmployeeServletIT.POSITION);
        params.put("grossSalary", String.valueOf(EmployeeServletIT.GROSS_SALARY));
        URL url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        String query = url.getQuery();
        url = new URL(url.toString().split("\\?")[0]);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        conn.getOutputStream().write(query.getBytes("ISO-8859-1"));
        
        int responseCode = conn.getResponseCode();
        assertEquals(HttpServletResponse.SC_MOVED_TEMPORARILY, responseCode);
        
        params = new HashMap<String, String>();
        params.put("action", WebActions.LIST);
        url = URLBuilder.build(baseURL, EmployeeServletIT.URL_PATH, params);
        String expectedLocation = url.toString();
        String location = conn.getHeaderField("Location");
        assertEquals(expectedLocation, location);
    }
}
