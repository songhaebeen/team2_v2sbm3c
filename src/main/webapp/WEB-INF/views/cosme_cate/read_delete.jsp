<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cosme_cate.Cosme_cateVO" %>

<%
// 삭제할 카테고리 정보를 읽어옴
Cosme_cateVO cosme_cateVO_read = (Cosme_cateVO)request.getAttribute("cosme_cateVO");
%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>

<link href="/css/style.css" rel="Stylesheet" type="text/css">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>화장품 종류 > 삭제</DIV>

<DIV class='content_body'>
  <DIV id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="cosme_cateno" value="<%=cosme_cateVO_read.getCosme_cateno() %>"> <%-- 삭제할 카테고리 번호 --%>
      

          <div class="msg_warning">삭제하면 복구 할 수 없습니다.</div>
          <label>화장품 종류 이름</label>: <%=cosme_cateVO_read.getCosme_catename() %>
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          

  

      <button type="button" onclick="location.href='/cosme_cate/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </FORM>
  </DIV>

  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 10%;'/>
      <col style='width: 50%;'/>
      <col style='width: 10%;'/>    
      <col style='width: 20%;'/>
      <col style='width: 10%;'/>
    </colgroup>
   
    <thead>  
    <TR>
      <TH class="th_bs">순서</TH>
      <TH class="th_bs">카테고리 이름</TH>
    </TR>
    </thead>
    
    <tbody>
    <%
    ArrayList<Cosme_cateVO> list = (ArrayList<Cosme_cateVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      Cosme_cateVO cosme_cateVO = list.get(i);
    %>
      <TR class="th_bs">
        <TD class='td_bs'><%= cosme_cateVO.getSeqno() %></TD>
        <TD><%=cosme_cateVO.getCosme_catename() %></TD>
        <TD class='td_bs'><%=cosme_cateVO.getCnt() %></TD>
        <TD class='td_bs'><%=cosme_cateVO.getRdate().substring(0, 10) %></TD>
        <TD>
          <A href="./read_update.do?cosme_cateno=<%=cosme_cateVO.getCosme_cateno() %>" title="수정"><IMG src="/cosme_cate/images/update.png" class="icon"></A>
          <A href="./read_delete.do?cosme_cateno=<%=cosme_cateVO.getCosme_cateno() %>" title="삭제"><IMG src="/cosme_cate/images/delete.png" class="icon"></A>
          <A href="./update_seqno_decrease.do?cosme_cateno=<%=cosme_cateVO.getCosme_cateno() %>" title="우선순위 높이기"><IMG src="/cosme_cate/images/decrease.png" class="icon"></A>
          <A href="./update_seqno_increase.do?cosme_cateno=<%=cosme_cateVO.getCosme_cateno() %>" title="우선순위 낮추기"><IMG src="/cosme_cate/images/increase.png" class="icon"></A>
          </TD>
      </TR>
    <%  
    }
    %>
    </tbody>
   
  </TABLE>
</DIV>

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

