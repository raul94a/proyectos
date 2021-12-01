<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="jo" class="controlador.JsltOperations"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <ul class="barra flex-row-around" >
     <c:if test="${cookie.tema.value != 'oscuro' or cookie.tema.value == null}">    
        <li id="btn-oscuro">
            <i class="far fa-moon"></i>
        </li>
     </c:if>
     <c:if test="${cookie.tema.value eq 'oscuro'}">    
       <li id="btn-claro">
         <i class="far fa-sun"></i>
       </li>
     </c:if>
    
     
    <li>

        <a href="index.jsp" class="index-nav"><i class="fas fa-home index-nav"
                style="display:block; text-align:center"></i>Inicio </a>

    </li>


    <li>
        <a href="subir.jsp"><i class="fas fa-upload" style="display:block; text-align:center"></i>Subir
            producto </a>
    </li>

    <li>
         <c:if test="${jo.hayAlgunMensajeSinLeer(sessionScope.usuario)}" >
        <a href="buzon.jsp" style="color:red"><i class="far fa-paper-plane rojo" style="display:block; text-align:center; color:red;"></i>Buzón(*)
        </a>
         </c:if>
         <c:if test="${!jo.hayAlgunMensajeSinLeer(sessionScope.usuario)}" >
        <a href="buzon.jsp" ><i class="far fa-paper-plane" style="display:block; text-align:center; "></i>Buzón
        </a>
         </c:if>
    </li>

    <li>
        <a href="perfil.jsp"><i class="far fa-smile" style="display:block; text-align:center"></i>Tú </a>
    </li>
    <li class="li-menu">
        <i class="fas fa-bars" style="display:none;"></i>
    </li>

</ul>