<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.mvc.cate.CateVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0,
                                 maximum-scale=5.0, width=device-width" /> 
<title>http://localhost:9091/cate/read.jsp?cateno=1</title>
<style type="text/css">
  *{ font-family: Malgun Gothic; font-size: 26px;}
</style>
</head>
<body>
<DIV style="font-size: 20px;">
<% CateVO cateVO = (CateVO) request.getAttribute("cateVO"); %>

 cateno:<%= cateVO.getCateno() %><br>
 name:<%= cateVO.getName() %><br>
 cnt :<%= cateVO.getCnt() %><br>
 rdate:<%= cateVO.getRdate() %><br>
</DIV>
</body>
</html>

