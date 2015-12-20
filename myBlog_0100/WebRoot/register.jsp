<%@ page contentType="text/html; charset=gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mess = (String) request.getAttribute("messages");
	if (mess == null || mess.equals(""))
		mess = "<li>新用户注册！</li>";
%>
<html>
	<head>
		<title>博主登录</title>
		<base href="<%=basePath%>">
		<link href="css/style.css" rel="stylesheet">
		<script language='javascript' src='js/validate.js'></script>
	</head>
	<body style="BACKGROUND-IMAGE: url(images/bg.jpg)">
		<center>
			<table width="777" height="523" border="1" cellpadding="0"
				cellspacing="0" background="images/logonBG.jpg">
				<tr>
					<td align="center">
						<form action="LogonAction?action=register" method="post" name="form1">
							<table border="0" cellpadding="0" cellspacing="0"
								style="margin-top: 300">
								<tr>
									<td colspan="2" align="center"><%=mess%></td>
								</tr>
								<tr height="30">
									<td>
										帐&nbsp;&nbsp;号：
									</td>
									<td>
										<input type="text" name="userName" style="width: 200" placeholder="帐号为字母或者数字">
									</td>
									<td>
										 <input type="button" class="btn_bg_validate" value="检测用户名" onclick="checkUserName(form1.userName.value)">
									</td>
								</tr>
								<tr height="30">
									<td>
										密&nbsp;&nbsp;码：
									</td>
									<td>
										<input type="password" name="userPass" style="width: 200" placeholder="密码至少为六位">
									</td>
								</tr>
								<tr height="30">
									<td>
										确认密码：
									</td>
									<td>
										<input type="password" name="userPass_again" style="width: 200">
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="btn_bg" value="确 定">
										<input type="reset" class="btn_bg" value="重 置">
										<a href="login.jsp">返回登陆界面</a>
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