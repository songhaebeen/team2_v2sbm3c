<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.ingred.IngredVO" %>

<%
// 삭제할 카테고리 정보를 읽어옴
IngredVO ingredVO_read = (IngredVO)request.getAttribute("ingredVO");
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
 
<DIV class='title_line'>화장품 성분 > 삭제</DIV>

<DIV class='content_body'>
  <DIV id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="ingredno" value="<%=ingredVO_read.getIngredno() %>"> <%-- 삭제할 카테고리 번호 --%>
      

          <div class="msg_warning">삭제하면 복구 할 수 없습니다.</div>
          <label>화장품 성분 이름</label>: <%=ingredVO_read.getIngredname() %>
          //
          <label>성분 효과</label>: <%=ingredVO_read.getIngredeffect() %>
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          

  

      <button type="button" onclick="location.href='/ingred/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </FORM>
  </DIV>

  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 15%;'/>
      <col style='width: 40%;'/>
      <col style='width: 15%;'/>    
      <col style='width: 10%;'/>
    </colgroup>
   
    <thead>  
    <TR>
      <TH class="th_bs">순서</TH>
      <TH class="th_bs">성분 이름</TH>
      <TH class="th_bs"> 성분 효과</TH>
      <TH class="th_bs">기타</TH>
    </TR>
    </thead>
    
    <tbody>
    <%
    ArrayList<IngredVO> list = (ArrayList<IngredVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      IngredVO ingredVO = list.get(i);
    %>
      <TR class="th_bs">
        <TD class='td_bs'><%= ingredVO.getSeqno() %></TD>
        <TD><%=ingredVO.getIngredname() %></TD>
        <TD><%=ingredVO.getIngredeffect() %></TD>
        <TD>
          <A href="./read_update.do?ingredno=<%=ingredVO.getIngredno() %>" title="수정"><IMG src="/cosme_cate/images/update.png" class="icon"></A>
          <A href="./read_delete.do?ingredno=<%=ingredVO.getIngredno() %>" title="삭제"><IMG src="/cosme_cate/images/delete.png" class="icon"></A>
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

