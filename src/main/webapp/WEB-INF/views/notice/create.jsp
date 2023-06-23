<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>공지사항 등록</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head>
 
<body>
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>공지사항 등록</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  <A href="./list_all.do?now_page=${param.now_page == null ? 1 : param.now_page}">목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>
  
  <FORM name='frm' method='POST' action='/notice/create.do'>
    
    <div>
       <label>제목</label>
       <input type='text' name='ntitle' value='공지사항' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='ncontent' required="required" class="form-control" rows="12" style='width: 100%;'>필독</textarea>
    </div>
    <div>
       <label>패스워드</label>
       <input type='password' name='passwd' value='1234' required="required" 
                 class="form-control" style='width: 50%;'>
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-info btn-sm">등록</button>
    </div>
  
  </FORM>
</DIV>
 </DIV>
  <jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>