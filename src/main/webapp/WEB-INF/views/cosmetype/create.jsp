<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>화장품 타입(기능) 등록</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
    
</head>
 
<body>
    <c:import url="../menu/header.jsp" />
 


<DIV class='content_body'>
  <DIV class='title_line'>화장품 타입(기능) 등록</DIV>
  
  <FORM name='frm' method='POST' action='/cosmetype/create.do'>
    <br><Br>
    <div>
       <label class="">타입 이름</label>
       <input type='text' name='cosmetypename'  placeholder='성분을 입력하세요' required="required" 
                class="form-control" style='width: 100%;'>
    </div>
    <br><Br>
    <div class="content_body_bottom">
      <button type="submit" class="my-btn btn">등록</button>
    </div>
  
  </FORM>
</DIV>
    <jsp:include page="../menu/footer.jsp" />
</body>
 
</html>