<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.List"%>
<%@page import="com.yxq.vo.ArticleBean;"%>
<html>
	<head>
		<title>������ҳ</title>
	</head>
	<body>
		<center>
			<table width="778" height="600" border="0" cellspacing="0"
				cellpadding="0" bgcolor="#F0EAED" style="word-break: break-all">
				<tr height="281">
					<td colspan="2"><jsp:include page="view/FrontTop.jsp" /></td>
				</tr>
				<tr>
					<td width="230" valign="top"><jsp:include
							page="view/FrontLeft.jsp" /></td>
					<td width="548" align="center" valign="top">
						<!-- ��ʾ �ҵ����� -->
						<table border="0" width="94%" rules="none" cellspacing="0"
							cellpadding="8" style="margin-top: 8">
							<tr height="40">
								<td>
									��R.oldmee�����¡�
								</td>
								<td align="right">
									<a href="front/article/ArticleIndex.jsp">����..</a>
								</td>
							</tr>
							<%
                           	List articleList=(List)request.getAttribute("articleList");
                        	if(articleList==null||articleList.size()==0){
                        %>
							<tr height="100">
								<td align="center">
									<li>
										����Ŀǰ��δ�����κ����£�
									</li>
								</td>
							</tr>
							<%  
                        	}
                        	else{
                         		int i=0;
                        		while(i<articleList.size()){
                        			ArticleBean articleSingle=(ArticleBean)articleList.get(i);
                        %>
							<tr>
								<td class="tdg" colspan="2">
									<a href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">
										<b><%=articleSingle.getTitle()%></b>
									</a>
									[<%=articleSingle.getCreateFrom()%>]
								</td>
							</tr>
							<tr height="10">
								<td class="tdg" colspan="2" valign="top"><%=articleSingle.getContent() %></td>
							</tr>
							<tr>
								<td style="text-indent: 20" colspan="2">
									<a
										href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">�Ķ�ȫ��</a>
								</td>
							</tr>
							<tr>
								<td align="right" colspan="2">
									����ʱ�䣺<%=articleSingle.getSdTime() %>
									| �Ķ���<%=articleSingle.getCount() %>
									��
								</td>
							</tr>
							<tr height="1">
								<td background="images/line.jpg" colspan="2"></td>
							</tr>
							<%
                        			i++;
                        		}
                        	}
                        %>
						</table>
					</td>
				</tr>
				<tr height="100">
					<td colspan="2"><%@ include file="view/FrontEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>