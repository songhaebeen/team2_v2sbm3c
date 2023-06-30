<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cateco.CatecoVO" %>

<%
CatecoVO catecoVO_read = (CatecoVO)request.getAttribute("catecoVO");
%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<jsp:include page="../menu/top.jsp" />
 
<DIV class='title_line'>전체 카테고리 > 수정</DIV>

<DIV class='content_body'>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='./update.do'>
      <input type="hidden" name="catecono" value="<%=catecoVO_read.getCatecono() %>">
      <label>카테고리 이름</label>
      <input type='text' name='name' value='<%=catecoVO_read.getName() %>' required="required" style='width: 25%;' autofocus="autofocus">
  
      <button type="submit" id='submit' class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>수정</button>
      <button type="button" onclick="location.href='/cateco/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
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
      <TH class="th_bs">카테고리</TH>
      <TH class="th_bs">카테고리 이름</TH>
      <TH class="th_bs">자료수</TH>
      <TH class="th_bs">등록일</TH>
      <TH class="th_bs">기타</TH>
    </TR>
    </thead>
    
    <tbody>
    <%
    ArrayList<CatecoVO> list = (ArrayList<CatecoVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      CatecoVO catecoVO = list.get(i);
    %>
      <TR>
        <TD class='td_bs'><%= catecoVO.getCatecono() %></TD>
        <TD><%=catecoVO.getName() %></TD>
        <TD class='td_bs'><%=catecoVO.getCnt() %></TD>
        <TD class='td_bs'><%=catecoVO.getRdate().substring(0, 10) %></TD>
        <TD><A href="./read_update.do?catecono=<%=catecoVO.getCatecono() %>" title="수정"><IMG src="/cosme_cate/images/update.png" class="icon"></A>/삭제</TD>
      </TR>
    <%  
    }
    %>
    </tbody>
   
  </TABLE>
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>
