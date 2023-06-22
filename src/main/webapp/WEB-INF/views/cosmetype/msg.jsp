<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>


<%-- /static/css/style.css --%> 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

</head> 
<body>

<c:import url="../menu/header.jsp" />

<%
// Object: 최상위 부모 타입 -> 원래의 데이터 타입으로 변경하여 사용 
String code = (String)request.getAttribute("code");
int cnt = (int)request.getAttribute("cnt");
%>

<DIV class='title_line'>카테고리 > 알림</DIV>
<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <%
      if (code.equals("create_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">화장품 타입 등록에 실패했습니다.</span></LI>
      <%  
      } else if (code.equals("update_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">화장품 타입 수정에 실패했습니다.</span></LI>
      <%  
      } else if (code.equals("delete_fail")) {
      %>
        <LI class='li_none'><span class="span_fail">화장품 타입 삭제에 실패했습니다.</span></LI>
      <%  
      }
      %>
      
      <LI class='li_none'>
        <br>
        <%
        if (cnt == 0) {
        %>
          <button onclick="history.back()" class="btn btn-danger">다시시도</button>
          
        <%
        } else {
        %>
          <button onclick="location.href='./create.do'" class="btn btn-info">새로운 화장품 타입 등록</button>
        <%
        }
        %>
        <button onclick="location.href='./list_all.do'" class="btn btn-info">전체 목록</button>
      </LI>
        
    </UL>
  </fieldset>

</DIV>

<jsp:include page="../menu/footer.jsp" flush='false' />
</body>

</html>

