<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>자유게시판</title>
<link rel="shortcut icon" href="/images/star.png" />

<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>
  자유게시판
  <c:if test="${param.word.length() > 0 }">
     「${param.word }」 검색 ${search_count } 건
  </c:if>
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  
      <A href="./create.do">등록</A>
      <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_all.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_grid.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">앨범형</A>
  </ASIDE>
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_all.do'>
      
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
                    onclick="location.href='./list_all.do?word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>

  <DIV class='menu_line'></DIV>
  
  <table class="table table-striped" style='width: 100%;'>
    <colgroup>
      <c:choose>
        <c:when test="${sessionScope.admin_id != null }">
          <col style="width: 10%;"></col>
          <col style="width: 50%;"></col>
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
 
    <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
  
</DIV>
 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

