package es.thefactory.hellojavaee8.util.web;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 
 * @author Pablo Lorenzo Manzano.
 *
 */
public class URLBuilder {
    
    /**
     * 
     * @param baseURL
     * @param path
     * @return URL
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public static URL build(URL baseURL, String path) throws MalformedURLException, UnsupportedEncodingException {
        return (URLBuilder.build(baseURL, path, null));
    }
    
    /**
     * 
     * @param baseURL
     * @param path
     * @param params
     * @return URL
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     */
    public static URL build(URL baseURL, String path, Map<String, String> params)
        throws MalformedURLException, UnsupportedEncodingException {
        String origin = baseURL.toString();
        
        if (origin.endsWith("/")) {
            origin = origin.substring(0, origin.length() - 1);
        }
        
        StringBuilder urlComponents = new StringBuilder(origin);
        urlComponents.append(path);
        
        if ((params != null) && (!(params.isEmpty()))) {
            StringBuilder query = new StringBuilder();
            query.append("?");
            
            for (Map.Entry<String, String> param : params.entrySet()) {
                if (query.length() > 1) {
                    query.append("&");
                }
                
                query.append(URLEncoder.encode(param.getKey(), "ISO-8859-1"));
                query.append("=");
                query.append(URLEncoder.encode(param.getValue(), "ISO-8859-1"));
            }
            
            urlComponents.append(query);
        }
        
        URL url = new URL(urlComponents.toString());
        
        return url;
    }
}
