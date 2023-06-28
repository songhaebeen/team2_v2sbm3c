<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="noticeno" value="${noticeVO.noticeno }" />
<c:set var="ntitle" value="${noticeVO.ntitle }" />        
<c:set var="ncontent" value="${noticeVO.ncontent }" />
<c:set var="file1" value="${noticeVO.file1 }" />
<c:set var="file1saved" value="${noticeVO.file1saved }" />
<c:set var="thumb1" value="${noticeVO.thumb1 }" />
<c:set var="size1" value="${noticeVO.size1 }" />
<c:set var="youtube" value="${noticeVO.youtube }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>수정</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
    
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
<script type="text/javascript">  

</script>
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
<DIV class='title_line'> ${ntitle } > 수정</DIV>
<br>

  <ASIDE class="aside_right">
    <A href="/notice/list_all.do">목록</A>
    <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>
  
  <FORM name='frm' method='POST' action='./update.do' enctype="multipart/form-data">
    <input type="hidden" name="noticeno" value="${noticeno }">
    
    <div>
       <label>제목</label>
       <input type='text' name='ntitle' value='${ntitle }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <Br>
    <div>
       <label>내용</label>
       <textarea name='ncontent' required="required" class="form-control" rows="12" style='width: 100%;'>${ncontent }</textarea>
    </div> 
    <Br>
    <div>
       <label>Youtube 스크립트</label>
       <textarea name='youtube' class="form-control" rows="2" style='width: 100%;'>${noticeVO.youtube}</textarea>
    </div>

    <c:choose>
      <c:when test="${sessionScope.admin_id == null }">
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
      <button type="submit" class="btn btn-info btn-sm">저장</button>
      <button type="button" onclick="location.href='./read.do?noticeno=${noticeno }'" class="btn btn-info btn-sm">취소</button>
    </div>
  
  </FORM>
</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>