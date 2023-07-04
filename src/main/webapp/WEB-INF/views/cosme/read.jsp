<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
 <c:set var="cosmeno" value="${cosmeVO.cosmeno }" />
<c:set var="brand" value="${cosmeVO.brand }" />
<c:set var="cosmename" value="${cosmeVO.cosmename }" />
<c:set var="rdate" value="${cosmeVO.rdate }" />
<c:set var="cosme_cateno" value="${cosme_cateVO.cosme_cateno }" />
<c:set var="adminno" value="${cosmeVO.adminno }" />
<c:set var="file1saved" value="${cosmeVO.file1saved }" />
<c:set var="thumb1" value="${cosmeVO.thumb1 }" />
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
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/cosme/storage/ --%>
              <img src="/cosme/storage/${file1saved }" style='width: 30%; float: center; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
                <img src="/cosme/images/logo2.gif" style='width: 30%; float: center; margin-top: 0.5%; margin-right: 1%;'>
            </c:otherwise>
          </c:choose>

          <br>
          <span style="font-size: 1.5em; font-weight: bold;">${cosmename }</span><br>
          <div style="font-size: 1em;">${brand } ${rdate }</div><br>
          <div style="font-size: 1em;">${cosmetypename } ${ingredname }</div><br>
      </li>
 
 
      <button type="button" onclick="location.href='http://localhost:9093/cosme_youtube_tip/list_youtube_cosmeno.do?cosmeno=${cosmeno }'" class="btn btn-info">유튜브 목록</button>
      
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>

 