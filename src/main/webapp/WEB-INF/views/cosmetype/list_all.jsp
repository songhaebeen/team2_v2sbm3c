<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cosmetype.CosmetypeVO" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>어떤 화장품을 원해💄</title>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>전체 화장품 타입</DIV>

<DIV class='content_body'>
  <DIV id='panel_create' style='padding: 10px 0px 10px 0px; background-color: #F9F9F9; width: 100%; text-align: center;'>
    <FORM name='frm_create' id='frm_create' method='POST' action='/cosmetype/create.do'>
      <label>화장품 타입 이름</label>
      <input type='text' name='cosmetypename' value='' required="required" style='width: 25%; margin-right: 10px;' autofocus="autofocus">
  
      <button type="submit" id='submit' class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>등록</button>
      <button type="button" onclick="location.href='/cosmetype/list_all.do'" class='btn btn-info btn-sm' style='height: 28px; margin-bottom: 5px;'>취소</button>
    </FORM>
  </DIV>

  <TABLE class='table table-hover'>
    <colgroup>
      <col style='width: 20%;'/>
      <col style='width: 45%;'/>
      <col style='width: 20%;'/>    
    </colgroup>
   
    <thead>  
      <tr>
        <th class="th_bs">순서</th>
        <th class="th_bs">화장품 타입 이름</th>
        <th class="th_bs">기타</th>
      </tr>
    </thead>


    
    <tbody>
    <%
    ArrayList<CosmetypeVO> list = (ArrayList<CosmetypeVO>)request.getAttribute("list");
    
    for (int i=0; i < list.size(); i++) {
      CosmetypeVO cosmetypeVO = list.get(i);
    %>
      <TR class="th_bs">
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
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>