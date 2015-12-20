<%@ page contentType="text/html; charset=gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mess = (String) request.getAttribute("messages");
	if (mess == null || mess.equals(""))
		mess = "<li>»¶Ó­µÇÂ¼£¡</li>";
%>
<html>
	<head>
		<title>ÓÃ»§µÇÂ¼</title>
		<base href="<%=basePath%>">
		<link href="css/style.css" rel="stylesheet">
	</head>
	<body style="BACKGROUND-IMAGE: url(images/bg.jpg)">
		<center>
			<table width="777" height="523" border="1" cellpadding="0"
				cellspacing="0" background="images/logonBG_login.jpg">
				<tr>
					<td align="center">
						<form action="LogonAction?action=logon" method="post">
							<table border="0" cellpadding="0" cellspacing="0"
								style="margin-top: 300">
								<tr>
									<td colspan="2" align="center"><%=mess%></td>
								</tr>
								<tr height="30">
									<td>
										ÕÊ&nbsp;&nbsp;ºÅ£º
									</td>
									<td>
										<input type="text" name="userName" style="width: 200">
									</td>
								</tr>
								<tr height="30">
									<td>
										ÃÜ&nbsp;&nbsp;Âë£º
									</td>
									<td>
										<input type="password" name="userPass" style="width: 200">
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="btn_bg" value="µÇ Â¼">
										<input type="reset" class="btn_bg" value="ÖØ ÖÃ">
										<a href="register.jsp">ÐÂÓÃ»§×¢²á</a>
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
			</table>
		</center>
	</body>
</html>