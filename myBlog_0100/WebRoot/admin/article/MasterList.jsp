<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yxq.vo.MasterBean" %>

<%
final int PAGE_SIZE = 7;
int  pageNo = Integer.parseInt(request.getParameter("pageNo"));

if(pageNo <= 0) pageNo = 0;

%>

<html>
<head>
	<title>���ͺ�̨-��������û�</title>
</head>
<body>
    <center>
        <table width="778" border="0" cellspacing="0" cellpadding="0" bgcolor="#FFFFFF" style="word-break:break-all">
            <tr><td colspan="2"><%@ include file="../view/AdminTop_admin.jsp" %></td></tr>
            <tr>
                <td><jsp:include page="/admin/view/AdminLeft_admin.jsp"/></td>
                <td align="center" valign="top">
                	<table width="560" border="0" cellspacing="0" cellpadding="0" rules="none">
						<tr height="60"><td colspan="4">����������û���</td></tr>
						<tr height="30" bgcolor="#F5F4F4">
							<td width="25%" style="text-indent:20">�û���</td>
							<td align="center" width="25%">�û����¹���</td>
							<td align="center" width="20%">�û��Ա�</td>
							<td align="center" width="15%">��Ȩ״̬</td>
							<td align="center"width="15%" colspan="2">����</td>
						</tr>
						<%
						    ArrayList masterlist=(ArrayList)request.getAttribute("masterlist");
							if(masterlist==null||masterlist.size()==0){
						%>
						<tr height="80"><td colspan="4" align="center"><li>û���û�����ʾ��</li></td></tr>
						<%	
							} 
							else{
								MasterBean single = null;
								if(pageNo+PAGE_SIZE-1 >= masterlist.size())
									pageNo = masterlist.size() - PAGE_SIZE;
    							for(int i=pageNo;i<pageNo+PAGE_SIZE;i++){
    								single =(MasterBean)masterlist.get(i);    							
						%>
						<tr height="35">
							<td style="text-indent:20"><%=single.getMaster_name()%></td>
							<td align="center"><a href="ArticleAction?pageNo=0&action=adminSelectList_admin&master_id=<%=single.getMaster_id() %>" class="word_purple">������û�</a></td>
							<td align="center"><%=single.getMaster_sex() %></td>
							<% 
						    boolean state = single.isMaster_state();
						    String str_state = state==false ? "δ��Ȩ" : "����Ȩ";
						    %>
							<td align="center"><%=str_state%></td>
							<td align="center"><a href="ArticleAction?action=masterModify&pageNo=<%=pageNo %>&masterId=<%=single.getMaster_id() %>" class="word_purple ">���޸�</a></td>
							<td align="center"><a href="ArticleAction?action=masterDelete&pageNo=<%=pageNo %>&masterId=<%=single.getMaster_id() %>" class="word_purple ">��ɾ��</a></td>							
						</tr>
						<%
    							}
    					%>
    					<tr height="40"><td></td></tr>
						<tr aligh="center">
							<td align="right">
								<a href="ArticleAction?action=masterSelect&pageNo=0">��һҳ</a>&nbsp;
								<a href="ArticleAction?action=masterSelect&pageNo=<%=pageNo-PAGE_SIZE %>">��һҳ</a>&nbsp;
							</td>
							
							<td align="left">
								<a href="ArticleAction?action=masterSelect&pageNo=<%=pageNo+PAGE_SIZE %>">��һҳ</a>&nbsp;
								<a href="ArticleAction?action=masterSelect&pageNo=<%=masterlist.size()-PAGE_SIZE %>">��ĩҳ</a>&nbsp;
							</td>
						</tr>
    					<%
							}
						%>
						
                	</table>
                </td>
            </tr>
            <tr><td colspan="2"><%@ include file="../view/AdminEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>