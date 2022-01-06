<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="es.thefactory.hellojavaee8.util.web.WebActions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Hello Java EE 8</title>
    </head>
    <body>
        <h1>Index</h1>
        <a href="helloworld">Click here</a> to get a greeting from the 'Hello World!' servlet.
        <br/>
        <a href="employee?action=${WebActions.LIST}">Click here</a> to get a list of employees.
    </body>
</html>
