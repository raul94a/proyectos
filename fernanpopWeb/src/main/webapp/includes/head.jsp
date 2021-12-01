<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>    
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- <meta http-equiv="refresh" content="20">-->
    <script src="https://kit.fontawesome.com/6aaba9f6ff.js" crossorigin="anonymous"></script>
    <title>FernanPop</title>
    <link rel="stylesheet" href="css/general.css">
    <link rel="stylesheet" href="css/main-mobile.css">
    <c:if test="${cookie.tema.value != 'oscuro' or cookie.tema.value == null}">   
        <link rel="stylesheet" href="css/tema-claro.css">
    </c:if>
    <c:if test="${cookie.tema.value eq 'oscuro'}">  
        <link rel="stylesheet" href="css/tema-oscuro.css">

    </c:if>    