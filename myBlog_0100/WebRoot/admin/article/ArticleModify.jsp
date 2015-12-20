<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yxq.vo.ArticleTypeBean" %>
<% ArrayList typelist=(ArrayList)session.getAttribute("artTypeList"); %>
<jsp:useBean id="modifySingle" class="com.yxq.vo.ArticleBean" scope="request"/>
<html>
<head>
	<title>博客后台-修改文章</title>
</head>
<body>
    <center>
        <table width="778" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"><%@ include file="../view/AdminTop.jsp" %></td></tr>
            <tr>
                <td  valign="top"><jsp:include page="../view/AdminLeft.jsp"/></td>
                <td align="center" valign="top">
                	<form action="ArticleAction" method="post">
                	<input type="hidden" name="action" value="modify">
                	<input type="hidden" name="type" value="doModify">
                	<input type="hidden" name="id" value="<%=modifySingle.getArticle_id() %>">
                	<table border="0" width="100%" cellspacing="0" cellpadding="8">
                		<tr height="30"><td colspan="2">【修改文章】</td></tr>
                		<tr>
                			<td align="center" width="20%">文章ID：</td>
                			<td><%=modifySingle.getArticle_id() %></td>
                		</tr>
                		<tr>
                			<td align="center">文章类别：</td>
                			<td>
                				<select name="typeId" style="width:100">
                					<option value=""></option>
                					<% 
                						if(typelist!=null&&typelist.size()!=0){
                							for(int i=0;i<typelist.size();i++){
                								ArticleTypeBean typeBean=(ArticleTypeBean)typelist.get(i);
                								if(typeBean.getArticleType_id()==modifySingle.getArticleType().getArticleType_id()){
                					%>
                					<option value="<%=typeBean.getArticleType_id()%>" selected><%=typeBean.getTypeName() %></option>
                					<%
                								}
                								else{
                									
                					%>
                					<option value="<%=typeBean.getArticleType_id()%>"><%=typeBean.getTypeName() %></option>
                					<%
                								}
                							}
                						}
                					%>
                				</select>
                			</td>
                		</tr>
                		<tr>
                			<td align="center">文章标题：</td>
                			<td><input type="text" name="title" value="<%=modifySingle.getTitle() %>" size="77"></td>
                		</tr>
                		<tr>
                			<td align="center">文章来源：</td>
                			<td>
                				<select name="create" style="width:100">
                					<option value=""/>
                					<%	if(modifySingle.getCreateFrom().equals("原创")){ %>
                					<option value="原创" selected>原创</option>
                					<option value="摘自">摘自</option>
                					<%	
                						}
	                					if(modifySingle.getCreateFrom().equals("摘自")){
	                				%>
	                				<option value="原创">原创</option>
                					<option value="摘自" selected>摘自</option>		
	                				<%	} %>
                				</select>
                			</td>
                		</tr>
	               		<tr>
                			<td align="center">文章描述：</td>
                			<td><input type="text" name="info" value="<%=modifySingle.getInfo()%>" size="77"></td>
                		</tr>
                		<tr>
                			<td align="center" valign="top">文章内容：</td>
                			<td>
                				<textarea name="content" rows="7" cols="65"><%=modifySingle.getContent() %></textarea>
                			</td>
                		</tr>
                		<tr height="25">
                			<td colspan="2" align="center">
                				<input type="submit" value="保存" class="btn_bg">
                				<input type="reset" value="重置" class="btn_bg">
                			</td>
                		</tr>
                	</table>
                	</form>
                </td>
            </tr>
            <tr><td colspan="2"><%@ include file="../view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>