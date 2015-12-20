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
	<title>聆音博客-所有文章</title>
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
                        	<td align="center"><li>没有文章类别可显示！</li></td>
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
			            	out.print("<tr height='60'><td align='center'>选择的文章类别中没有任何文章！</td></tr>");
            			else{      
            				
            				/**通过第一篇文章找到它的类别名这里的“0”可以换成list内的任意值**/
                     		ArticleBean articleSingle=(ArticleBean)articlelist.get(0);
                     		int books=articlelist.size();
                    		out.print("<tr height='60'><td align='center'>【"+articleSingle.getArticleType().getTypeName()+" 共 "+books+" 篇】</td></tr>");                            
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
 							    ▲
	 							<a href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">
 									<b><%=articleSingle.getTitle() %></b>
 								</a>
 								[<%=articleSingle.getCreateFrom()%>]
 							</td>
 						</tr>
 						<tr height="60"><td colspan="2" valign="top"><%=articleSingle.getContent() %></td></tr>
 						<tr height="30"><td style="text-indent:20" colspan="2"><a href="ArticleAction?action=read&article_id=<%=articleSingle.getArticle_id()%>">阅读全文</a></td></tr>
 						<tr>
 							<td align="right" colspan="2">
 								发表时间：<%=articleSingle.getSdTime() %> |
 								阅读：<%=articleSingle.getCount() %> 次
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
							<a href="ArticleAction?action=select&pageNo=0&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">第一页</a>&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=pageNo-PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">上一页</a>&nbsp;
							&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=pageNo+PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">下一页</a>&nbsp;
							<a href="ArticleAction?action=select&pageNo=<%=articlelist.size()-PAGE_SIZE %>&typeId=<%=articleSingle.getArticleType().getArticleType_id() %>">最末页</a>&nbsp;
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