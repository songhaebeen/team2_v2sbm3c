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
<title>유튜브 조회</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'><A href="./list_youtube_cosmeno.do?cosmeno=${cosmeno }" class='title_link'>${cosmeVO.cosmename }</A></DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9093/cosme_youtube_tip/create.do?cosmeno=1
      --%>
      <A href="./create.do?cosmeno=${cosmeVO.cosmeno }">등록</A>
      <span class='menu_divide' >│</span>
      <A href="./update.do?youtubeno=${youtubeno}&now_page=${param.now_page}&word=${param.word }">글 수정</A>
      <span class='menu_divide' >│</span>
      <A href="./delete.do?youtubeno=${youtubeno}&now_page=${param.now_page}&cosmeno=${cosmeno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>

    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_youtube_cosmeno.do?cosmeno=${cosmeno }&now_page=${param.now_page}&word=${param.word }">기본 목록형</A>    
  </ASIDE> 
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_youtube_cosmeno.do'>
      <input type='hidden' name='cosmeno' value='${cosmeVO.cosmeno }'>  <%-- 게시판의 구분 --%>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_youtube_cosmeno.do?cosmeno=${cosmeVO.cosmeno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <span style="font-size: 1.5em; font-weight: bold;">${youtubetitle }</span><br>
          <div style="font-size: 1em;">${youtubename } ${rdate }</div><br>
          ${youtubecontent }
        </DIV>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>  
    </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

 