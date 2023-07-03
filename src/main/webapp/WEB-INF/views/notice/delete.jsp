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
           
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>삭제</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
    
<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
<DIV class='title_line'> ${ntitle } 삭제</DIV>
  <br>
  
  <ASIDE class="aside_right">
     <A href="./create.do">등록</A>
     <span class='menu_divide' >│</span>
    <A href="./update.do?noticeno=${noticeno}">수정</A> 
     <span class='menu_divide' >│</span>
    <A href="/notice/list_all.do">목록</A>
      <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>

 
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">

        <DIV style='text-align: center; width: 100%; float: center;'>
             <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <img src="/notice/storage/${file1saved }" style='width: 20%;'> 
            </c:when>
            <c:otherwise> <!-- 이미지가 없는 경우 -->
              이미지가 없습니다.
            </c:otherwise>
          </c:choose>
          </DIV>
      <br>
        <DIV style='text-align: center; width: 100%; float: center;'>
          <span style='font-size: 1.5em;'>${ntitle}</span>
          <c:if test="${size1 > 0 }">
            <br>삭제되는 파일: ${file1 }
          </c:if>
          <br>
          <FORM name='frm' method='POST' action='./delete.do'>
              <input type='hidden' name='noticeno' value='${noticeno}'>
              <input type='hidden' name='now_page' value='${param.now_page}'>
              <br><br>
              <div style='text-align: center; margin: 10px auto;'>
                <span style="color: #FF0000; font-weight: bold;">삭제를 진행 하시겠습니까? 삭제하시면 복구 할 수 없습니다.</span><br><br>
                <br><br>
                <button type = "submit" class="btn btn-primary">삭제 진행</button>
                <button type = "button" onclick = "history.back()" class="btn btn-primary">취소</button>
              </div>   
          </FORM>
        </DIV>
      </li>
     </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>