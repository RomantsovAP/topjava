<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Meals</title>
    <style>
        .normal {color: green;}
        .exceeded {color: red;}
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table border=1>
    <thead>
    <tr>
        <th>description</th>
        <th>calories</th>
        <th>dateTime</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${mealsTo}" var="mealTo">
        <jsp:useBean id="mealTo" scope="page" type="ru.javawebinar.topjava.model.MealTo" />
        <tr class = "${mealTo.excess ? 'exceeded' : 'normal'}">
            <td><c:out value="${mealTo.description}"/></td>
            <td><c:out value="${mealTo.calories}"/></td>
            <td><c:out value="${mealTo.dateTime.format(formatter)}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>