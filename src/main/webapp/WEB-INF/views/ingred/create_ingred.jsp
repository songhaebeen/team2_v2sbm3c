<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>성분 등록</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
    
</head>
 
<body>
    <c:import url="../menu/header.jsp" />
 


<DIV class='content_body'>
  <DIV class='title_line'>성분 등록</DIV>
  
  <FORM name='frm' method='POST' action='/ingred/create.do'>
    <br><Br>
    <div>
       <label class="">성분 이름</label>
       <input type='text' name='ingredname'  placeholder='성분을 입력하세요' required="required" 
                class="form-control" style='width: 100%;'>
    </div>
    <br><Br>
    <div>
       <label>효과</label>
             <input type='text' name='ingredeffect'  placeholder='효과을 입력하세요' required="required" 
                class="form-control" style='width: 100%;'>
    </div>

    <div class="content_body_bottom">
      <button type="submit" class="my-btn btn">등록</button>
      <button type="button" class="my-btn btn" onclick="#'" class="btn btn-primary">목록</button>
    </div>
  
  </FORM>
</DIV>
    <jsp:include page="../menu/footer.jsp" />
</body>
 
</html>