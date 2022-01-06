<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%@ page import="es.thefactory.hellojavaee8.util.web.WebActions" %>

<!DOCTYPE html>
<html>
    <head>
        <title>Hello Java EE 8</title>
    </head>
    <body>
        <h1>Employee</h1>
        <form action="employee" method="post">
            <input name="employeeId" type="hidden" value="${employeeDto.employeeId}"/>
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input name="name" required type="text" value="${employeeDto.name}"/></td>
                </tr>
                <tr>
                    <td>Last Name:</td>
                    <td><input name="lastName" required type="text" value="${employeeDto.lastName}"/></td>
                </tr>
                <tr>
                    <td>Position:</td>
                    <td><input name="position" required type="text" value="${employeeDto.position}"/></td>
                </tr>
                <tr>
                    <td>Gross Salary:</td>
                    <td><input min="0" name="grossSalary" type="number" value="${employeeDto.grossSalary}"/></td>
                </tr>
                <tr>
                    <td colspan="2" style="text-align: right;">
                        <input type="submit" value="OK"/>
                        <a href="employee?action=${WebActions.LIST}">Cancel</a>
                    </td>
                </tr>
            </table>
        </form>
    </body>
</html>
