<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="dev.mvc.cateco.CatecoVO" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, minimum-scale=1.0,
                                 maximum-scale=5.0, width=device-width" /> 
<title>http://localhost:9093/cateco/read.jsp?catecono=1</title>
<style type="text/css">
  *{ font-family: Malgun Gothic; font-size: 26px;}
</style>
</head>
<body>
<DIV style="font-size: 20px;">
<% CatecoVO catecoVO = (CatecoVO) request.getAttribute("catecoVO"); %>

 catecono:<%= catecoVO.getCatecono() %><br>
 name:<%= catecoVO.getName() %><br>
 cnt :<%= catecoVO.getCnt() %><br>
 rdate:<%= catecoVO.getRdate() %><br>
</DIV>
</body>
</html>