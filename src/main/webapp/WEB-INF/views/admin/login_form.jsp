<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Resort world</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>


</head> 
 
<body>
<c:import url="/menu/top.do" />
 
  <DIV class='title_line'>관리자 로그인</DIV>

  <DIV class='content_body'> 
    <DIV style='width: 40%; margin: 0px auto;'>
      <FORM name='frm' method='POST' action='./login.do'>
      
        <div class="form-group">
          <label>아이디:</label> 
          <input type='text' class="form-control" name='id' id='id' required="required" 
                    style='width: 80%;' placeholder="아이디" autofocus="autofocus" value="">
        </div>   
     
        <div class="form-group">
          <label>패스워드:</label> 
          <input type='password' class="form-control" name='passwd' id='passwd' 
                    required="required" style='width: 80%;' placeholder="패스워드" value="">
        </div>   
     
        <button type="submit" class="btn btn-info">로그인</button>
        
      </FORM>
    </DIV>
  </DIV> <%-- <DIV class='content_body'> END --%>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

