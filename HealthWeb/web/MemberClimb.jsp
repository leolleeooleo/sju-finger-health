<%-- 
    Document   : climb
    Created on : 2012/6/29, 下午 11:59:24
    Author     : Leo
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="edu.sju.ee98.health.web.Manager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>健康點點名</title>
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
                <h1>登山紀錄</h1>
                <%
                    ArrayList<String> record = Manager.SQL().selectRecord(user.getUser());
                    for (int i = 0; i < record.size(); i++) {
                %>
                <p><%=record.get(i)%></p>
                <% }%>
            </div>
            <%@ include file="Copyright.html" %>
        </div>
    </body>
</html>