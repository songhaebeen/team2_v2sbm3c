<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cateco.CatecoVO" %>

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
 
<DIV class='title_line'>전체 카테고리</DIV>

<DIV class='content_body'>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='./create.do'>
      <label>카테고리 이름</label>
      <input type='text' name='name' value='' required="required" style='width: 25%;' autofocus="autofocus">
  
      <button type="submit" id='submit' class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>등록</button>
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
        <TD class='td_bs'><%= catecoVO.getSeqno() %></TD>
        <TD><%=catecoVO.getName() %></TD>
        <TD class='td_bs'><%=catecoVO.getCnt() %></TD>
        <TD class='td_bs'><%=catecoVO.getRdate().substring(0, 10) %></TD>
        <TD>
          <A href="./read_update.do?catecono=<%=catecoVO.getCatecono() %>" title="수정"><IMG src="/cosme_cate/images/update.png" class="icon"></A>
          <A href="./read_delete.do?catecono=<%=catecoVO.getCatecono() %>" title="삭제"><IMG src="/cosme_cate/images/delete.png" class="icon"></A>
          <A href="./update_seqno_decrease.do?catecono=<%=catecoVO.getCatecono() %>" title="우선순위 높이기"><IMG src="/cosme_cate/images/decrease.png" class="icon"></A>
          <A href="./update_seqno_increase.do?catecono=<%=catecoVO.getCatecono() %>" title="우선순위 낮추기"><IMG src="/cosme_cate/images/increase.png" class="icon"></A>
        </TD>
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