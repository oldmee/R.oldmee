<%@ page language="java" contentType="text/html; charset=gb2312"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.yxq.vo.ArticleBean" %>
<%@ page import="com.yxq.vo.ArticleTypeBean" %>

<%
final int PAGE_SIZE = 3;
int  pageNo = Integer.parseInt(request.getParameter("pageNo"));
if(pageNo <= 0) pageNo = 0;

%>

<html>
<head>
	<title>��������-��������</title>
</head>
<body>
    <center>
        <table width="778" height="600" border="0" cellspacing="0" cellpadding="0" bgcolor="#F0EAED">
            <tr height="281"><td colspan="2"><jsp:include page="../view/FrontTop.jsp" /></td></tr>
            <tr>
                <td width="230" valign="top"><jsp:include page="../view/FrontLeft.jsp"/></td>
                <td width="548" align="center" valign="top">
                    <table width="100%" height="55" border="0" cellsapcing="0" cellpadding="0" background="images/main_B.jpg">
                        <tr align="center" style="padding-bottom:10">
                        	<%
                        		ArrayList typelist=(ArrayList)session.getAttribute("artTypeList");
                        	ArticleTypeBean single = null;
                        		if(typelist==null||typelist.size()==0){
                        	%>
                        	<td align="center"><li>û������������ʾ��</li></td>
                        	<%	
                        		}
                        		else{
                        			for(int i=0;i<typelist.size();i++){
                        				single=(ArticleTypeBean)typelist.get(i);
                        	%>
                        	<td><a href="ArticleAction?action=select&pageNo=0&typeId=<%=single.getArticleType_id() %>"><%=single.getTypeName() %></a></td>
                        	<%
                        			}
                        		}
                        	%>
                        </tr>      
                    </table>
                    <table width="95%" border="0" cellsapcing="0" cellpadding="0">
                  	<% 
			        	ArrayList articlelist=(ArrayList)request.getAttribute("articleList"); 
            			if(articlelist==null||articlelist.size()==0)
			            	out.print("<tr height='60'><td align='center'>ѡ������������û���κ����£�</td></tr>");
            			else{      
            				
            				/**ͨ����һƪ�����ҵ��������������ġ�0�����Ի���list�ڵ�����ֵ**/
                     		ArticleBean articleSingle=(ArticleBean)articlelist.get(0);
                     		int books=articlelist.size();
                    		out.print("<tr height='60'><td align='center'>��"+articleSingle.getArticleType().getTypeName()+" �� "+books+" ƪ��</td></tr>");                            
            				int i=pageNo;
            				int max = pageNo+PAGE_SIZE;
            				if(i+PAGE_SIZE-1 >= articlelist.size())
								i = articlelist.size() - PAGE_SIZE;
            				if(max>articlelist.size()) {
            					max = articlelist.size();
            					pageNo = max - PAGE_SIZE;
            				}
            				if(i<0)
            					i=articlelist.size() - PAGE_SIZE;
            				if(i<0)
            					i = 0;
                        	while(i<max){
                        		articleSingle=(ArticleBean)articlelist.get(i);            
                        %>
 						<tr height="30">
 							<td colspan="2">
 							    ��
	 							<a href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">
 									<b><%=articleSingle.getTitle() %></b>
 								</a>
 								[<%=articleSingle.getCreateFrom()%>]
 							</td>
 						</tr>
 						<tr height="60"><td colspan="2" valign="top"><%=articleSingle.getContent() %></td></tr>
 						<tr height="30"><td style="text-indent:20" colspan="2"><a href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">�Ķ�ȫ��</a></td></tr>
 						<tr>
 							<td align="right" colspan="2">
 								����ʱ�䣺<%=articleSingle.getSdTime() %> |
 								�Ķ���<%=articleSingle.getCount() %> ��
 							</td>
 						</tr>
		                <tr height="1"><td background="images/line.jpg" colspan="2"></td></tr>
                        <%
                        			i++;
                        		}
                        %>
                        <tr height="40"><td></td></tr>
						<tr>
							
							<td align="right">
							<a href="ArticleAction?action=select&pageNo=0&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">��һҳ</a>&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=pageNo-PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">��һҳ</a>&nbsp;
							&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=pageNo+PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">��һҳ</a>&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=articlelist.size()-PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">��ĩҳ</a>&nbsp;
							</td>
							
						</tr>
                        <%
                        	}
            			%>
            			<tr><td>
            		</table>
                </td>
            </tr>
            <tr height="100"><td colspan="2"><%@ include file="../view/FrontEnd.jsp" %></td></tr>
        </table>
    </center>
</body>
</html>