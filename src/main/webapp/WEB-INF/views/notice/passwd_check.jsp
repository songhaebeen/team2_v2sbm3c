<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>패스워드 불일치</title>
  
<link href="/css/style.css" rel="Stylesheet" type="text/css">
   
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head>
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='message'>
    <H4>패스워드가 일치하지 않습니다.</H4>
    <BR><BR>
    <button type='button' onclick="history.back()" class="my-btn btn">다시 시도</button>       
  </DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>