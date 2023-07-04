<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>유튜브 메세지</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>알림</DIV>
 
 <c:set var= "code" value= "${param.code }" /> <%--mav.addObject("code", "create_success"); --%>
 <c:set var="cnt" value="${param.cnt }" />     <%-- mav.addObject("cnt", cnt); --%>
 <c:set var="youtubeno" value="${param.youtubeno }" /> <%-- mav.addObject("youtubeno", cosme_youtube_tipVO..getYoutubeno()); // redirect parameter 적용 --%>

<DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <c:choose>
          <c:when test="${code == 'youtube_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">새로운 유튜브 등록에 실패했습니다.</span>
            </LI>                                                                      
          </c:when>
          <c:when test="${code == 'update_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">유튜브 수정에 실패했습니다.</span>
            </LI>                                                                      
          </c:when>
        </c:choose>

      <LI class='li_none'>
        <br>
        <c:choose>
            <c:when test="${cnt == 0 }">
                <button type='button' onclick="history.back()" class="btn btn-primary">다시 시도</button>    
            </c:when>
        </c:choose>
        
        <button type='button' onclick="location.href='./create.do?youtubeno=${youtubeno}'" class="btn btn-info btn-sm">새로운 유튜브 등록</button>        
      </LI>
    </UL>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

 