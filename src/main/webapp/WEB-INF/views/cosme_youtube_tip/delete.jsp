<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<c:set var="youtubeno" value="${cosme_youtube_tipVO.youtubeno }" />
<c:set var="cosmeno" value="${cosme_youtube_tipVO.cosmeno }" />
<c:set var="youtubetitle" value="${cosme_youtube_tipVO.youtubetitle }" />
<c:set var="youtubecontent" value="${cosme_youtube_tipVO.youtubecontent }" />
<c:set var="word" value="${cosme_youtube_tipVO.word }" />
<c:set var="youtube" value="${cosme_youtube_tipVO.youtube }" />
<c:set var="rdate" value="${cosme_youtube_tipVO.rdate.substring(0, 16) }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>유튜브 삭제</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
 <DIV class='title_line'><A href="./list_youtube_cosmeno.do?cosmeno=${cosmeno }" class='title_link'>${cosmeVO.cosmename }</A> > ${youtubetitle } 삭제</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <A href="./create.do?cosmeno=${cosmeno }">등록</A>
    <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>
    <A href="./list_youtube_cosmeno.do?cosmeno=${v }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./update.do?youtubeno=${youtubeno}">수정</A>
  </ASIDE> 
  
  <%-- 검색 폼 --%>
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_youtube_cosmeno.do'>
      <input type='hidden' name='cosmeno' value='${cosmeno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' style='width: 20%;'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' style='width: 20%;'>
        </c:otherwise>
      </c:choose>
      <button type='submit'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' 
                     onclick="location.href='./list_youtube_cosmeno.do?cosmeno=${cosmeno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">

        <DIV style='text-align: left; width: 47%; float: left;'>

          <FORM name='frm' method='POST' action='./delete.do'>
              <input type='hidden' name='youtubeno' value='${youtubeno}'>
              <input type='hidden' name='cosmeno' value='${cosmeno}'>
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
 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

 