<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>유튜브 등록</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
  <FORM name='frm' method='POST' action='/cosme_youtube_tip/create.do' enctype="multipart/form-data"> <!-- /cosme_youtube_tip 폴더 자동 인식, 권장 -->
  <input type="hidden" name="cosmeno" value="${param.cosmeno }">
  <DIV class='title_line'>유튜브 등록</DIV>
    
    <div>
       <label class="">제목</label>
       <input type='text' name='youtubetitle' placeholder='유튜브 제목' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label class="">내용</label>
       <input type='text' name='youtubecontent' placeholder='관련 내용' required="required" 
                 class="form-control" style='width: 100%;'>
    </div>
    <br>
    <div>
       <label>Youtube 스크립트</label>
       <textarea name='youtube' class="form-control" rows="12" style='width: 100%;'>${cosme_youtube_tipVO.youtube }</textarea>
    </div>


    <div class="content_body_bottom">
      <button type="submit" class="btn btn-info">등록</button>
      <button type="button" onclick="location.href='./list_by_type.do'" class="btn btn-info">목록</button>
    </div>
  
  </FORM>
</DIV>
 

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>