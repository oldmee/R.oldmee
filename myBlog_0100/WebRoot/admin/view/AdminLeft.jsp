<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>���ͺ�̨-����</title>
		<base href="<%=basePath%>">
	</head>
	<body>
		<table width="193" height="401" border="0" cellpadding="0"
			cellspacing="0" bgcolor="#FFFFFF">
			<tr>
				<td width="191" height="401" valign="top">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
						<tr>
							<td height="32" colspan="4" align="center"
								background="<%=path%>/images/adminLmenu_1.jpg"
								class="word_menuHead">
								���¹���
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30" class="tableBorder_B">
								<a href="<%=basePath%>admin/article/ArticleAdd.jsp">�﷢������</a>
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30">
								<a href="<%=basePath%>ArticleAction?pageNo=0&action=adminSelectList">�����/�޸�/ɾ������
								
							</td>
						</tr>
						<tr>
							<td height="30" colspan="4" align="center"
								background="<%=path%>/images/adminLmenu_2.jpg"
								class="word_menuHead">
								����������
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30" class="tableBorder_B">
								<a href="<%=basePath%>admin/article/ArticleTypeAdd.jsp">��������</a>
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30">
								<a href="<%=basePath%>ArticleAction?action=typeSelect">�����/�޸�/ɾ�����</a>
							</td>
						</tr>
					</table>
				</td>
				<td valign="top" bgcolor="#FFFFFF" class="tableBorder_R">
					&nbsp;
				</td>
			</tr>
		</table>
	</body>
</html>