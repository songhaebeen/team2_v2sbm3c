<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>W
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>자유게시판 등록</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>자유게시판 등록</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>
    <A href="./list_all.do?fboardno=${fboardno }">목록</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_grid.do?fboardno=${fboardno }">갤러리</A>
   
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>
  
  <FORM name='frm' method='POST' action='/fboard/create.do' enctype="multipart/form-data">
    <br>
    
    <div>
       <label>제목</label>
       <input type='text' name='ftitle' value='추천함!' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label>내용</label>
       <textarea name='fcontent' required="required" class="form-control" rows="12" style='width: 100%;'>실제 써본 후기임</textarea>
    </div>
    <div>
       <label>이미지</label>
       <input type='file' class="form-control" name='file1MF' id='file1MF' 
                 value='' placeholder="파일 선택">
    </div>   
    <div>
       <label>패스워드</label>
       <input type='password' name='passwd' value='1234' required="required" 
                 class="form-control" style='width: 50%;'>
    </div>   
    <div class="content_body_bottom">
      <button type="submit" class="my-btn btn">등록</button>
      <button type="button" onclick="location.href='/fboard/list_all.do'" class="my-btn btn">목록</button>
    </div>
  
  </FORM>
</DIV>
 </DIV>
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>