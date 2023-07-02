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
  
      <A href="./create.do?">등록</A>
      <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_all.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_grid.do?now_page=${param.now_page == null ? 1 : param.now_page}&word=${param.word }">앨범형</A>
  </ASIDE>
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_grid.do'>
      
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
  
  <div style='width: 100%; text-align: left;'> <%-- 갤러리 Layout 시작 --%>
      <c:forEach var="fboardVO" items="${list}" varStatus="status">
        <c:set var="ftitle" value="${fboardVO.ftitle.trim() }" />
        <c:set var="fcontent" value="${fboardVO.fcontent.trim() }" />
        <c:set var="fboardno" value="${fboardVO.fboardno }" />
        <c:set var="memberno" value="${fboardVO.memberno }" />
        <c:set var="thumb1" value="${fboardVO.thumb1 }" />
        <c:set var="size1" value="${fboardVO.size1 }" />
        <c:set var="rdate" value="${fboardVO.rdate.substring(0, 10) }" />
        
      <%-- 하나의 행에 이미지를 5개씩 출력후 행 변경, index는 0부터 시작 --%>
      <c:if test="${status.index % 10 == 0 && status.index != 0 }"> 
        <HR class='menu_line'> <%-- 줄바꿈 --%>
      </c:if>
        
      <!-- 4기준 하나의 이미지, 24 * 4 = 96% -->
      <!-- 5기준 하나의 이미지, 19.2 * 5 = 96% -->
      <div onclick="location.href='./read.do?fboardno=${fboardno }&word=${param.word }&now_page=${param.now_page == null ? 1 : param.now_page }'" class='hover'  
             style='width: 320px; height: 298px; float: left; margin: 0.3%; padding: 0.3%; background-color: #EAEAEA; text-align: left;'>
        
        <c:choose> 
          <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}"> <%-- 이미지인지 검사 --%>
            <%-- registry.addResourceHandler("/fboard/storage/**").addResourceLocations("file:///" +  Fboard.getUploadDir()); --%>
            <img src="/fboard/storage/${thumb1 }" style="width: 100%; height: 210px;">
          </c:when>
          <c:otherwise> <!-- 이미지가 없는 경우 기본 이미지 출력: /static/fboard/images/none1.png -->
            <IMG src="/fboard/images/logo2.gif" style="width: 100%; height: 210px;">
          </c:otherwise>
        </c:choose>
        
        <strong>
          <c:choose> 
            <c:when test="${ftitle.length() > 25 }"> <%-- 25 이상이면 25자만 출력, 공백:&nbsp; 6자 --%>
              ${ftitle.substring(0, 25)}.....
            </c:when>
            <c:when test="${ftitle.length() <= 25 }">
              ${ftitle}
            </c:when>
          </c:choose>
        </strong>
        <br>
        
        <div style='font-size: 0.95em; word-break: break-all;'>
            <img src="/member/images/user.png" style="height: 16px"> <%--${memberno } --%>
        </div>
         ${rdate }
      </div>
      
    </c:forEach>
  </div>
  
  <!-- 페이지 목록 출력 부분 시작 -->
  <DIV class='bottom_menu'>${paging }</DIV> <%-- 페이지 리스트 --%>
  <!-- 페이지 목록 출력 부분 종료 -->
  
</DIV>

 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>

