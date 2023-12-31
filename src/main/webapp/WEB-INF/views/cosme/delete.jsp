<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>화장품 삭제</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
  <DIV class='title_line'>화장품 삭제</DIV>
  
  <FORM name='frm' method='POST' action='/cosme/delete.do' enctype="multipart/form-data">
    <input type="hidden" name="cosmeno" value="${param.cosmeno }">


    <div class="content_body_bottom">
      <span style="color: #FF0000; font-weight: bold;">삭제를 진행 하시겠습니까? 삭제하시면 복구 할 수 없습니다.</span><br><br>
      <button type="submit" class="btn btn-primary">삭제 진행</button>
      <button type = "button" onclick = "history.back()" class="btn btn-primary">취소</button>
    </div>
  
  </FORM>
</DIV>
 

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>