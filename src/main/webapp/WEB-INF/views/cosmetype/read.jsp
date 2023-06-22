<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.mvc.cosmetype.CosmetypeVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0,
                                 maximum-scale=5.0, width=device-width" /> 
<title>Cosmetype Read</title>
<style type="text/css">
  *{ font-family: Malgun Gothic; font-size: 26px;}
</style>
</head>
<body>
<DIV style="font-size: 20px;">
<% CosmetypeVO cosmetypeVO = (CosmetypeVO) request.getAttribute("cosmetypeVO"); %>

 cosmetypeno:<%= cosmetypeVO.getCosmetypeno() %><br>
 cosmetypename:<%= cosmetypeVO.getCosmetypename() %><br>
</DIV>
</body>
</html>

