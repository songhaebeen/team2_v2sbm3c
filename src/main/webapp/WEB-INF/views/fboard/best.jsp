<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>best</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
</head>
 
 <link rel = "stylesheet" href = "/css/bootstrap.css">
 
  <DIV class='menu_line'></DIV>
  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <c:choose>
        <c:when test="${sessionScope.admin_id != null }">
          <col style="width: 10%;"></col>
          <col style="width: 50%;"></col>
          <col style="width: 10%;"></col>     
          <col style="width: 10%;"></col>   
          <col style="width: 20%;"></col>  
        </c:when>
        <c:otherwise>
          <col style="width: 10%;"></col>
          <col style="width: 60%;"></col>
          <col style="width: 10%;"></col>    
          <col style="width: 20%;"></col>
        </c:otherwise>
      </c:choose>
    </colgroup>
    
              
          <thead>
      <tr>
        <th style='text-align: center;'></th>
        <th style='text-align: left;'>제목</th>
        <th style='text-align: center;'>조회수</th>
        <th style='text-align: center;'>등록일</th>
      </tr>
    
    </thead>
    
    <tbody>
      <c:forEach var="fboardVO" items="${list}">
        <c:set var="ftitle" value="${fboardVO.ftitle }" />
        <c:set var="fcontent" value="${fboardVO.fcontent }" />
        <c:set var="fboardno" value="${fboardVO.fboardno }" />
        <c:set var="thumb1" value="${fboardVO.thumb1 }" />
        <c:set var="views" value="${fboardVO.views }" />
          <c:set var="rdate" value="${fboardVO.rdate.substring(0, 10) }" />
        
         <tr style="height: 112px;" onclick="location.href='./read.do?fboardno=${fboardno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'>
          <td style='vertical-align: middle; text-align: center; '>
            <c:choose>
              <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
                 <%-- registry.addResourceHandler("/fboard/storage/**").addResourceLocations("file:///" +  Fboard.getUploadDir()); --%>
                <img src="/fboard/storage/${thumb1 }" style="width: 140px; height: 110px;">
              </c:when>
              <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/fboard/images/logo2.gif -->
                <IMG src="/fboard/images/logo2.gif" style="width: 140px; height: 110px;">
              </c:otherwise>
            </c:choose>
          </td>  
          <td style='vertical-align: middle;'>
            <div style='font-weight: bold;'><%--<a href="./read.do?fboardno=${fboardno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }">--%>${ftitle }</a></div>
            <c:choose> 
              <c:when test="${fcontent.length() > 140 }"> <%-- 140자 이상이면 140자만 출력 --%>
                  ${fcontent.substring(0, 140)}...더보기
              </c:when>
              <c:when test="${fcontent.length() <= 140 }">
                ${fcontent}
              </c:when>
            </c:choose>
          </td> 
          
            <td style='vertical-align: middle; text-align: center;'>
            <div style='font-weight: bold;'>${views }</div>
          </td>
          
            <td style='vertical-align: middle; text-align: center;'>
            <div style='font-weight: bold;'>${rdate }</div>
          </td>
          
          <c:choose>
            <c:when test="${sessionScope.admin_id != null }"> 
              <td style='vertical-align: middle; text-align: center;'>
                <A href="/fboard/delete.do?fboardno=${fboardno}&now_page=${param.now_page == null ? 1 : param.now_page}" title="삭제"><IMG src="/fboard/images/delete.png" class="icon"></A>
              </td>
            </c:when>
            <c:otherwise>
            
            </c:otherwise>
          </c:choose>

        </tr>
        
      </c:forEach>

    </tbody>
  </table>
 
<jsp:include page="../menu/bottom.jsp" />
</body>
</html>