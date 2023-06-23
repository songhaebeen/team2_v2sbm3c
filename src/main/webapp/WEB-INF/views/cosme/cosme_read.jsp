<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <c:set var="cosmeno" value="${cosmeVO.cosmeno }" />
<c:set var="brand" value="${cosmeVO.brand }" />
<c:set var="cosmename" value="${cosmeVO.cosmename }" />
<c:set var="rdate" value="${cosmeVO.rdate }" />
<c:set var="cosme_cateno" value="${cosme_cateVO.cosme_cateno }" />
<c:set var="adminno" value="${cosmeVO.adminno }" />
<c:set var="adminno" value="${cosmeVO.adminno }" />
<c:set var="adminno" value="${cosmeVO.cosme_file_saved }" />
<c:set var="cosme_cateno" value="${cosmeVO.cosme_file_preview }" />
<c:set var="cosme_youtube" value="${cosmeVO.cosme_youtube }" />
<c:set var="cosmetypename" value="${CosmetypeVO.cosmetypename }" />
<c:set var="ingredname" value="${IngredVO.ingredname }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>화장품 내용</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'><A href="./list_all.do?=${cosme_cateno }" class='title_link'>${cosme_cateVO.cosme_catename }</A></DIV>
 
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all;">
          <c:choose>
            <c:when test="${cosme_file_preview.endsWith('jpg') || cosme_file_preview.endsWith('png') || cosme_file_preview.endsWith('gif')}">
              <%-- /static/cosme/storage/ --%>
              <img src="/cosme/storage/${cosme_file_saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/cosme/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${cosmename }</span><br>
          <div style="font-size: 1em;">${brand } ${rdate }</div><br>
          <div style="font-size: 1em;">${cosmetypename } ${ingredname }</div><br>
          <div style="font-size: 1em;">${cosme_youtube }</div><br>
        </DIV>
      </li>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

 