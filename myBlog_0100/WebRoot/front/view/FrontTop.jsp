<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%@page import="com.yxq.vo.MasterBean"%>
<%@page import="com.yxq.vo.AuthorReviewBean"%>
<%@page import="java.util.*"%>

<%
	String name = session.getAttribute("MyName").toString(); 
	//MasterBean mb = (MasterBean)session.getAttribute("111");
	MasterBean mb = (MasterBean)session.getAttribute("111");
	Set<AuthorReviewBean> st = mb.getAuthorReview();
	//Set<AuthorReviewBean> st = mb.getAuthorReview();
	//Iterator<AuthorReviewBean> it = st.iterator();
	
%>
<html>
    <head>
    	<title>博客-页头</title>
		<base href="<%=basePath%>">
		<link type="text/css" rel="stylesheet" href="<%=path%>/css/style.css">	    	
    </head>
	<body background="<%=path%>/images/bg.jpg">
    	<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
        	<tr>
            	<td background="images/top.jpg">
                	<table border="0" cellspacing="10" cellpadding="0" style="margin-top:145;margin-left:350">
                    	<tr>
                    		<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <br></td>
                    		<%
                    		
                    		if(!st.isEmpty()) {
                    		 %>
                    		<%if(!name.equals("kevin_lee")) { %>
                    		<td class="topTD"><a href="<%=basePath%>front/article/message.jsp" class="topA">@你有消息</a> |</td>
                    		<%}} %>
                        	<td class="topTD"><a href="<%=basePath%>index.jsp" class="topA">博客首页</a> |</td>
                        	
                        	<%if(name.equals("kevin_lee")){%>
            	            <td class="topTD"><a href="<%=basePath%>admin/AdminIndex.jsp" class="topA">管理博客</a> | </td>
            	            <%} %>
            	            
            	            <td class="topTD"><a href="<%=basePath%>login.jsp" class="topA">退出</a></td>
                	    </tr>
	                </table>
    	        </td>
        	</tr>
	    </table>
	</body>
</html>