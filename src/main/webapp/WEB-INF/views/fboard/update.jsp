<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="ftitle" value="${fboardVO.ftitle }" />
<c:set var="fcontent" value="${fboardVO.fcontent }" />
<c:set var="fboardno" value="${fboardVO.fboardno }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>글 수정</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head>
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'> ${ftitle } > 수정</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="./create.do">등록</A>
    <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>
    <A href="./list_all.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_grid.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">앨범형</A>
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>
  
  <FORM name='frm' method='POST' action='./update.do'>
    <input type="hidden" name="fboardno" value="${fboardno }">
    <input type="hidden" name="now_page" value="${param.now_page }">
    
    <div>
       <label>제목</label>
       <input type='text' name='ftitle' value='${ftitle }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <Br>
    <div>
       <label>내용</label>
       <textarea name='fcontent' required="required" class="form-control" rows="12" style='width: 100%;'>${fcontent }</textarea>
    </div>
        <Br>
    <div>
       <label>Youtube 스크립트</label>
       <textarea name='youtube' class="form-control" rows="2" style='width: 100%;'>${fboardVO.youtube}</textarea>
    </div>
    <c:choose>
      <c:when test="${sessionScope.member_id == null }">
        <div>
          <label>패스워드</label>
          <input type='password' name='passwd' value='1234' required="required" 
                    class="form-control" style='width: 30%;'>
        </div>
      </c:when>
      <c:otherwise>
      </c:otherwise>
    </c:choose>
       
    <div class="content_body_bottom">
      <button type="submit" class="btn btn-primary">저장</button>
      <button type="button" onclick="location.href='./read.do?fboardno=${fboardno }'" class="btn btn-primary">취소</button>
    </div>
  
  </FORM>
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

