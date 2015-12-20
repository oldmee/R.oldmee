<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
	if(session.getAttribute("admin")==null)
	{
		out.println("请先"+"<a href='../admin/login_admin.jsp'>登录！</a>");
		return;
	}
%>
<html>
<head>
	<title>博客后台首页</title>
</head>
<body>
    <center>
        <table width="777" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"><%@ include file="view/AdminTop_admin.jsp" %></td></tr>
            <tr>
                <td width="180" valign="top"><jsp:include page="view/AdminLeft_admin.jsp"/></td>
                <td align="center" valign="top" bgcolor="#FFFFFF">&nbsp;</td>
            </tr>
            <tr><td colspan="2"><%@ include file="view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>