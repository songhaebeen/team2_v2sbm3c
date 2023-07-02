<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>화장품 등록 여부</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>알림</DIV>

<c:set var="code" value="${param.code }" /> <%--mav.addObject("code", "create_success"); --%>
<c:set var="cnt" value="${param.cnt }" />     <%-- mav.addObject("cnt", cnt); --%>
<c:set var="cosmeno" value="${param.cosmeno }" /> <%-- mav.addObject("cosmeno", cosmeVO..getCosmeno()); // redirect parameter 적용 --%>
 
 <DIV class='message'>
  <fieldset class='fieldset_basic'>
    <UL>
      <c:choose>
          <c:when test="${code == 'create_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">새로운 화장품 등록에 실패했습니다.</span>
            </LI>                                                                      
          </c:when>
          <c:when test="${code == 'create_success'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">새로운 화장품 등록에 성공했습니다.</span>
            </LI>                                                                      
          </c:when>
           <c:when test="${code == 'update_file_success'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 이미지 수정에 성공했습니다.</span>
            </LI>                                                                      
          </c:when>
           <c:when test="${code == 'update_file_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 이미지 수정에 실패했습니다.</span>
            </LI>                                                                      
          </c:when>
          <c:when test="${code == 'update_success'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 수정에 성공했습니다.</span>
            </LI>                                                                      
          </c:when>
           <c:when test="${code == 'update_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 수정에 실패했습니다.</span>
            </LI>                                                                      
          </c:when>
           <c:when test="${code == 'cosme_delete_success'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 삭제에 성공했습니다.</span>
            </LI>                                                                      
          </c:when>
           <c:when test="${code == 'cosme_delete_fail'}"> <%-- Java if --%>
            <LI class='li_none'>
              <span class="span_fail">기존 화장품 삭제에 실패했습니다.</span>
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
        
        <button type='button' onclick="location.href='./create.do?cosmeno=${cosmeno}'" class="btn btn-info btn-sm">새로운 화장품 등록</button>        
      </LI>
    </UL>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

 