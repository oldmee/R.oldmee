<%@ page language="java" contentType="text/html; charset=gb2312"%>
<jsp:useBean id="masterSingle" class="com.yxq.vo.MasterBean"
	scope="request" />
<html>
	<head>
		<title>博客后台-修改类别</title>
	</head>
	<body>
		<center>
			<table width="778" border="0" cellspacing="0" cellpadding="0"
				bgcolor="#FFFFFF" style="word-break: break-all">
				<tr>
					<td colspan="2"><%@ include file="../view/AdminTop_admin.jsp"%></td>
				</tr>
				<tr>
					<td valign="top"><jsp:include page="../view/AdminLeft_admin.jsp" /></td>
					<td align="center" valign="top">
						<form action="ArticleAction" method="post">
							<input type="hidden" name="action" value="masterModify">
							<input type="hidden" name="type" value="doModify">
							<input type="hidden" name="masterId" value="<%=masterSingle.getMaster_id()%>">
							<table border="0" width="99%" cellspacing="0" cellpadding="8">
								<tr height="60">
									<td colspan="2">
										【修改用户权限】
									</td>
								</tr>
								<tr>
									<td align="center" width="20%">
										用户ID号：
									</td>
									<td><%=masterSingle.getMaster_id()%></td>
								</tr>
								<tr>
									<td align="center" width="20%">
										用户名称：
									</td>
									<td><%=masterSingle.getMaster_name()%></td>
								</tr>
								<tr>
									<td align="center" width="25%">
										授权状态：
									</td>
									
									<td>
										<input type="radio" name="state" value="false"
											<%if(masterSingle.isMaster_state()==false) {%>
												checked="checked";<%} %>/>
										未授权
										<input type="radio" name="state" value="true"
											<%if(masterSingle.isMaster_state()==true) {%>
												checked="checked";<%} %>/>
										已授权
									</td>
								</tr>
								<tr height="50">
									<td align="center">
										<input type="submit" value="修改" class="btn_bg">
									</td>
									<td align="left">
										<input type="reset" value="重置" class="btn_bg">
									</td>
								</tr>
							</table>
						</form>
					</td>
				</tr>
				<tr>
					<td colspan="2"><%@ include file="../view/AdminEnd.jsp"%></td>
				</tr>
			</table>
		</center>
	</body>
</html>