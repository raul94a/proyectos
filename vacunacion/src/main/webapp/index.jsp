
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.ArrayList"%>
<jsp:useBean id="op" class="controlador.Operaciones"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="assets/styles/main.css">
        <title>AndaVac</title>
    </head>
    <body>
        <header class="row header">
            
            <img src="assets/img/logo-consejeria-75.png" class="img-header">
            <h2 class="titulo-header">Click <span>Salud+</span></h2>
        </header>
        <div class="contenedor-principal">
            <div class="info">
                <c:if test="${sessionScope.a eq null}"> 
                    <p class="centrar">Aplicación Web vacunación COVID-19 Junta de Andalucía Style.</p>
                    <p class="centrar">Para registrar la vacunación de una persona pulsa sobre <strong>Personal sanitario</strong></p>
                    <p class="centrar">Para ver tu estado de vacunación pulsa sobre <strong>Ciudadano</strong></p>
                </c:if>
                <c:if test="${sessionScope.a eq 's'}">
                    <p class="centrar">Logearse como personal sanitario</p>
                </c:if>
                 <c:if test="${sessionScope.a eq 'c'}">
                    <p class="centrar">Logearse como ciudadano</p>
                </c:if>
                <c:if test="${sessionScope.a eq 'sanitario'}">
                    <p class="centrar">Usted está logeado como personal sanitario. Por favor, introduzcla los datos del ciudadano que vaya a ser vacunado.</p>
                </c:if>
                <c:if test="${sessionScope.a eq 'ciudadano'}">  
                    <p class="centrar">Datos de vacunación del paciente con DNI ${sessionScope.dni}</p>
                </c:if>
            
            </div>

             <h1 style="text-align:center;font-family:Arial;letter-spacing:2px;color:#000066;margin:3% 0 2% 0;">Elige una opción para acceder al sistema</h1>
            <nav class="row">
                <a href="${pageContext.request.contextPath}/Servlet?a=s" class="btnNavegacion">Personal sanitario</a>
                <a href="${pageContext.request.contextPath}/Servlet?a=c" class="btnNavegacion w25 centrar">Ciudadano</a>
            </nav>
            <main>  
                <c:if test="${sessionScope.a eq 's'}">
                     <div class="mensaje">
                         <p class="centrar">${sessionScope.unsuccessful}</p>
                    </div>
                    <h2 class="centrar">Introduce tus datos</h2>
                    <form action="${pageContext.request.contextPath}/Servlet" method="POST" class="form-app column">
                        <label class="centrar">NSS</label>
                        <input type="text" name="id" required maxlength="10">
                        <input type="hidden" name="modo" value="s">
                        <input type="submit" value="Enviar" class="btnSecundario">
                    </form>
                </c:if>
                <c:if test="${sessionScope.a eq 'c'}">
                     <h2 class="centrar">Introduce tus datos</h2>
                    <form action="${pageContext.request.contextPath}/Servlet" method="POST" class="form-app column">
                        <label>DNI</label>
                        <input type="text" name="id" required maxlength="9">
                        <input type="hidden" name="modo" value="c">
                        <input type="submit" value="Enviar" class="btnSecundario">
                    </form>
                </c:if>
                <c:if test="${sessionScope.a eq 'sanitario'}">
                    <c:if test="${sessionScope.mensaje != null}">
                    <div class="mensaje">
                        <p class="centrar">${sessionScope.mensaje}</p>
                    </div>
                    </c:if>
                    <form action="${pageContext.request.contextPath}/ServletVacunacion" method="POST" class="form-app column">
                        <label class="centrar">DNI del paciente</label>
                        <input type="text" name="dni" required maxlength="9" value="${sessionScope.dni != null ? sessionScope.dni : ''}" required>
                        <c:if test="${sessionScope.dni != null}">
                            <c:set var="usuario" value="${op.usuario(sessionScope.dni) != null ? op.usuario(sessionScope.dni) : null}"/>
                            <label>Nombre</label>
                            <input type="text" name="nombre" value="${usuario != null ? usuario.getNombre() : ''}" required>
                            <label>Apellidos</label>
                            <input type="text" name="apellidos" value="${usuario != null ? usuario.getApellidos() : ''}" required>
                            <label>Tipo de vacuna</label>
                            <select name="vacuna" required>
                                <c:if test="${usuario eq null}">  
                                    <c:forEach var="vacuna" items="${op.vacunasDisponibles()}">
                                    <option value="${vacuna.id}">${vacuna.nombre}</option>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${usuario != null}">
                                    <option value="${usuario.getVacuna().getId()}">${usuario.getVacuna().getNombre()}</option>

                                </c:if>
                            </select>
                            <label>Municipio de vacunacion</label>
                            <input type="text" name="municipio" required></label>
                            <input type="hidden" name="continue" value="true">
                        </c:if>
                        <input type="submit" value="Enviar" class="btnSecundario">
                    </form>
                </c:if>
                <c:if test="${sessionScope.a eq 'ciudadano'}">
                    <c:choose>  
                        <c:when test="${empty sessionScope.vacunaciones}">
                            <div class="mensaje">El/la ciudadano/a con DNI ${sessionScope.dni} no ha recibido ningun vacuna contra el SARS-CoV-2</div>
                        </c:when>
                        <c:when test="${not empty sessionScope.vacunaciones}">
           
                            <table class="tabla">
                                <tr>
                                    <th colspan="2">Vacunaciones del ciudadano con DNI ${sessionScope.dni}</th>
                                </tr>
                          
                            <c:forEach var="usuario" items="${sessionScope.vacunaciones}" varStatus="counter">  
                               
                                <c:if test="${counter.count eq 1}">
                                <tr>
                                    <td colspan="2" class="centrar">Primera dosis</td>
                                </tr>
                                 
                                </c:if>
                                <c:if test="${counter.count eq 2}">
                                <tr>
                                    <td colspan="2" class="centrar">Segunda dosis</td>
                                </tr>

                                </c:if>
                                <tr>
                                    <td>Vacuna administrada</td>
                                    <td>${usuario.getVacuna().getNombre()}</td>
                                </tr>
                                <tr>
                                    <td>Fecha de administración</td>
                                    <td>${usuario.getFecha()}</td>
                                </tr>
                                <tr>
                                    <td>Municipio</td>
                                    <td>${usuario.getMunicipio()}</td>
                                </tr>
                              
                            </c:forEach>
                            </table>
                            <c:if test="${sessionScope.certificado eq true}">   
                                <a href="covidCertificates/covidCertificates26052027L.pdf" download="factura.pdf" class="btnSecundario downloadDoc">Descargar certificado de vacunación</a>
                            </c:if>
                        </c:when>
                    </c:choose>
                </c:if>

            </main>
        </div>
    <footer>
            <p>Proyecto realizado por Raúl Albín Alba</p>
            <p style="font-weight: bolder">Grado Superior en Desarrolo de Aplicaciones Multiplataforma</p>
            <p>IES Fernando III, Martos (Jaén)</p>
        </footer>
    </body>
</html>
