<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import="dev.mvc.ingred.IngredVO" %>
 
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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>
<DIV style="font-size: 20px;">
<% IngredVO ingredVO = (IngredVO) request.getAttribute("ingredVO"); %>

 ingredno:<%= ingredVO.getIngredno() %><br>
 ingredname:<%= ingredVO.getIngredname() %><br>
 ingredeffect:<%= ingredVO.getIngredeffect() %><br>
</DIV>
</body>
</html>

