<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="com.yxq.vo.ArticleBean" %>
<html>
<head>
	<title>���ͺ�̨��ҳ</title>
</head>
<body>
    <center>
        <table width="778" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"><%@ include file="../view/AdminTop_admin.jsp" %></td></tr>
            <tr>
                <td><jsp:include page="../view/AdminLeft_admin.jsp"/></td>
                <td align="center" valign="top">
                    <% 
                    	ArticleBean single=(ArticleBean)request.getAttribute("articleSingle");
                    	if(single==null)
                    		out.println("�Ķ�����ʧ�ܣ�");
                    	else{
                    %>
                	<table border="0" width="585" rules="all" cellpadding="0" cellspacing="0">
                	    <tr height="35" align="right">
                  	        <td background="images/admin_sonTop.jpg">
		               	         ����ʱ�䣺<%=single.getSdTime() %>&nbsp;&nbsp;&nbsp;&nbsp;
                  	        	���ۣ�<%=single.getReviews().size() %> ��&nbsp;&nbsp;&nbsp;&nbsp;
                	        	�Ķ���<%=single.getCount() %> ��&nbsp;&nbsp;&nbsp;&nbsp;
                	    	</td>
                	    </tr>
                	    <tr height="15"><td></td></tr>
                	    <tr><td align="center"><b><font style="font-size:25px"><%=single.getTitle()%></font></b></td></tr>
                	    <tr><td align="right"><%=single.getCreateFrom()%>��<%=single.getInfo() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>
                	    <tr height="15"><td></td></tr>
                	    <tr height="200"><td valign="top" style="padding-left:20;padding-right:20"><%=single.getContent() %></td></tr>
                	</table>
					<input type="button" value="��&nbsp;��" class="btn_bg" onClick="javascript:window.history.go(-1)">
                	<%	} %>
                </td>
            </tr>
            <tr><td colspan="2"><%@ include file="../view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>