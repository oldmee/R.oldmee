<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
	<head>
		<title>博客后台-侧栏</title>
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
								文章管理
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30" class="tableBorder_B">
								★发表文章
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30">
								★浏览/修改/删除文章
							</td>
						</tr>
						<tr>
							<td height="30" colspan="4" align="center"
								background="<%=path%>/images/adminLmenu_2.jpg"
								class="word_menuHead">
								文章类别管理
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30" class="tableBorder_B">
								★添加类别
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30">
								★浏览/修改/删除类别
							</td>
						</tr>
						<tr>
							<td height="30" colspan="4" align="center"
								background="<%=path%>/images/adminLmenu_2.jpg"
								class="word_menuHead">
								用户管理
							</td>
						</tr>
						<tr valign="bottom">
							<td height="27" colspan="4" bgcolor="#E59FD5"
								style="text-indent: 30">
								<a href="<%=basePath%>ArticleAction?action=masterSelect&pageNo=0">★浏览/修改/删除用户</a>
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