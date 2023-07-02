<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>관리자용 화장품 카테고리별 목록</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_lie'>
  ${cosme_cateVO.cosme_catename }
</DIV>

<DIV class='content_body'>
  <ASIDE class='aside_right'>

  <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
  <c:if test="${sessionScope.admin_id != null }">
    <%--
    http://localhost:9093/cosme/create.do?cosme_cateno=1
    http://localhost:9093/cosme/create.do?cosme_cateno=2
    http://localhost:9093/cosme/create.do?cosme_cateno=3
     --%>
     <A href="./create.do?cosme_cateno=${cosme_cateVO.cosme_cateno }">등록</A>
     <span class='menu_divide' >│</span>
  </c:if>
  
    <A href="javascript:location.reload();">새로고침</A>
  </ASIDE>
  
  <DIV class='menu_line'></DIV>

  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <col style="width: 10%;"></col>
      <col style="width: 80%;"></col>
      <col style="width: 10%;"></col>
    </colgroup>
    
    <tbody>
      <c:forEach var="cosmeVO" items="${list}">
        <c:set var="cosmename" value="${cosmeVO.cosmename }" />
        <c:set var="brand" value="${cosmeVO.brand }" />
        <c:set var="cosme_cateno" value="${cosmeVO.cosme_cateno }" />
        <c:set var="cosmeno" value="${cosmeVO.cosmeno }" />
        <c:set var="thumb1" value="${cosmeVO.thumb1 }" />
        
         <tr style="height: 112px;" onclick="location.href='./read.do?cosmeno=${cosmeno }'" class='hover'>
          <td style='vertical-align: middle; text-align: center; '>
      <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                <%-- registry.addResourceHandler("/cosme/storage/**").addResourceLocations("file:///" +  Cosme.getUploadDir()); --%>
                <img src="/cosme/storage/${thumb1 }" style="width: 120px; height: 90px;">
              </c:when>
              <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력 -->
                <IMG src="/cosme/images/logo2.gif" style="width: 120px; height: 90px;">
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <div style='font-weight: bold;'>${cosmename }</div>
          </td> 
        </tr>
        
      </c:forEach>
    </tbody>

</DIV>
 

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

 