<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>team2</title>
<link rel="shortcut icon" href="/images/logo2.gif" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head>
<body>
<%-- <jsp:include page="../menu/top.jsp" flush='false' /> --%>
<c:import url="/menu/top.do" />

  <DIV style='width: 100%; margin: 30px auto; text-align: center;'>
    <%-- /static/images/resort01.jpg --%>
    <iframe src='./jquery/fotorama-4.6.4/example.html' style='width: 108%; height: 600px; border: none;'></iframe>
    <!-- 
    <IMG src='/images/logo2.gif' style='width: 45%;'>
     -->
  </DIV>
  
  <DIV style='margin: 0px auto; width: 100%;'>
    <DIV style='float: left; width: 100%;'>
     </DIV>
     <DIV style='float: left; width: 100%;'>
    </DIV>  
  </DIV>
 
  <DIV style='width: 100%; margin: 0px auto;'>
  </DIV>  
 
<jsp:include page="./menu/bottom.jsp" flush='false' />
 
</body>
</html>


