<%@ page contentType="text/html; charset=gb2312"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String mess = (String) request.getAttribute("messages");
	if (mess == null || mess.equals(""))
		mess = "<li>���û�ע�ᣡ</li>";
%>
<html>
	<head>
		<title>������¼</title>
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
										��&nbsp;&nbsp;�ţ�
									</td>
									<td>
										<input type="text" name="userName" style="width: 200" placeholder="�ʺ�Ϊ��ĸ��������">
									</td>
									<td>
										 <input type="button" class="btn_bg_validate" value="����û���" onclick="checkUserName(form1.userName.value)">
									</td>
								</tr>
								<tr height="30">
									<td>
										��&nbsp;&nbsp;�룺
									</td>
									<td>
										<input type="password" name="userPass" style="width: 200" placeholder="��������Ϊ��λ">
									</td>
								</tr>
								<tr height="30">
									<td>
										ȷ�����룺
									</td>
									<td>
										<input type="password" name="userPass_again" style="width: 200">
									</td>
								</tr>
								<tr>
									<td></td>
									<td>
										<input type="submit" class="btn_bg" value="ȷ ��">
										<input type="reset" class="btn_bg" value="�� ��">
										<a href="login.jsp">���ص�½����</a>
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