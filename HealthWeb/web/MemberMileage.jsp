<%-- 
    Document   : mileage
    Created on : 2012/6/30, 下午 02:58:26
    Author     : Leo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="edu.sju.ee98.health.web.Manager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>首頁</title>
        <link href="./css/base.css" rel="stylesheet" type="text/css" />
    </head>

    <body class="ColFixLtHdr">

        <div id="container">
            <div id="header">
                <h1>健康點點名</h1>
            </div>
            <%@ include file="MenuList.html" %>
            <%@ include file="MemberList.html" %>
            <div id="mainContent">
                <jsp:useBean id="user" class="edu.sju.ee98.health.web.beans.UserBean" scope="session"/>
                <h1>里程統計</h1>
                <%
                    int total = Manager.SQL().plusPoints(user.getUser());
                    int expend = Manager.SQL().costPoints(user.getUser());
                %>
                <p>里程總數：<%=total%></p>
                <p>里程使用：<%=expend%></p>
                <p>里程累計：<%=total - expend%></p>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>