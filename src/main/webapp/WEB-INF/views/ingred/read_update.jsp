<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.ingred.IngredVO" %>

<%
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
 
<DIV class='title_line'>화장품 성분 > 수정</DIV>

<DIV class='content_body'>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='./update.do'>
      <input type="hidden" name="ingredno" value="<%=ingredVO_read.getIngredno() %>">
      <label>화장품 성분 이름</label>
      <input type='text' name='ingredname' value='<%=ingredVO_read.getIngredname() %>' required="required" style='width: 25%;' autofocus="autofocus">
      <label>성분 효과</label>
      <input type='text' name='ingredeffect' value='<%=ingredVO_read.getIngredeffect() %>' required="required" style='width: 25%;' autofocus="autofocus">
      <button type="submit" id='submit' class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>수정</button>
      <button type="button" onclick="location.href='/ingred/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
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
      <TH class="th_bs">성분 이름</TH>
      <TH class="th_bs"> 성분 효과</TH>
    </TR>
    </thead>
    
    <tbody>
    <% 
    ArrayList<IngredVO> list = (ArrayList<IngredVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      IngredVO ingredVO = list.get(i);
    %>
      <TR class="th_bs">
        <TD class='td_bs'><%= ingredVO.getIngredno() %></TD>
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

