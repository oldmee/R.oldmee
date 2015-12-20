<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.List"%>
<%@page import="com.yxq.vo.MasterBean"%>
<%@page import="com.yxq.vo.ArticleBean"%>
<html>
	<head>
		<title>博客-侧栏</title>
	</head>
	<body>
		<center>
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td valign="top">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr height="29">
								<td background="images/left_T.jpg"
									style="text-indent: 45; padding-top: 8">
									<b><font color="white">我的信息</font> </b>
								</td>
							</tr>
							<tr height="40">
								<td background="images/left_M.jpg" style="text-indent: 30">
									我的昵称：<%=session.getAttribute("MyName")%>
								</td>
							</tr>
							<tr height="16">
								<td background="images/left_E.jpg" bgcolror="";></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td valign="top">

					</td>
				</tr>
				<tr>
					<td></td>
				</tr>
			</table>
		</center>
	</body>
</html>