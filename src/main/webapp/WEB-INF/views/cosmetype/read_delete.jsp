<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cosmetype.CosmetypeVO" %>

<%
// 삭제할 카테고리 정보를 읽어옴
CosmetypeVO cosmetypeVO_read = (CosmetypeVO)request.getAttribute("cosmetypeVO");
%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>

<link href="/css/style.css" rel="Stylesheet" type="text/css">
    
</head> 
 
<body>
<c:import url="../menu/header.jsp" />
 
<DIV class='title_line'>화장품 타입 > 삭제</DIV>

<DIV class='content_body'>
  <DIV id='panel_delete' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_delete' id='frm_delete' method='POST' action='./delete.do'>
      <input type="hidden" name="cosmetypeno" value="<%=cosmetypeVO_read.getCosmetypeno() %>"> <%-- 삭제할 카테고리 번호 --%>
      

          <div class="msg_warning">삭제하면 복구 할 수 없습니다.</div>
          <label>화장품 타입 이름</label>: <%=cosmetypeVO_read.getCosmetypename() %>
      
          <button type="submit" id='submit' class='btn btn-warning btn-sm' style='height: 28px; margin-bottom: 5px;'>삭제</button>          

  

      <button type="button" onclick="location.href='/cosmetype/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
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
    ArrayList<CosmetypeVO> list = (ArrayList<CosmetypeVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      CosmetypeVO cosmetypeVO = list.get(i);
    %>
      <TR>
        <TD class='td_bs'><%= cosmetypeVO.getCosmetypeno() %></TD>
        <TD><%=cosmetypeVO.getCosmetypename() %></TD>
        <TD>
          <A href="./read_update.do?cosmetypeno=<%=cosmetypeVO.getCosmetypeno() %>" title="수정"><IMG src="/cosme_cate/images/update.png" class="icon"></A>
          <A href="./read_delete.do?cosmetypeno=<%=cosmetypeVO.getCosmetypeno() %>" title="삭제"><IMG src="/cosme_cate/images/delete.png" class="icon"></A>
        </TD>
      </TR>
    <%  
    }
    %>
    </tbody>
   
  </TABLE>
</DIV>

 
<jsp:include page="../menu/footer.jsp" />
</body>
 
</html>

