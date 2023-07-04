<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>공지사항</title>
<link rel="shortcut icon" href="/images/star.png" />

<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'>
  공지사항
  <c:if test="${param.word.length() > 0 }">
     「${param.word }」 검색 ${search_count } 건
  </c:if>
</DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
  
      <A href="./create.do">등록</A>
      <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>
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
      <c:forEach var="noticeVO" items="${list}">
      <c:set var="noticeno" value="${noticeVO.noticeno }" />
      <c:set var="ntitle" value="${noticeVO.ntitle }" />        
      <c:set var="ncontent" value="${noticeVO.ncontent }" />
      <c:set var="thumb1" value="${noticeVO.thumb1 }" />
      <c:set var="views" value="${noticeVO.views }" />
      <c:set var="rdate" value="${noticeVO.rdate.substring(0, 10) }" />
        
   <tr style="height: 112px;" onclick="location.href='./read.do?noticeno=${noticeno }&now_page=${param.now_page == null ? 1 : param.now_page}'" class='hover'>
          <td style='vertical-align: middle; text-align: center; '>
          <IMG src="/notice/images/check.png" style="width: 17px; height: 17px;">     
          </td> 
          
          <td style='vertical-align: middle; '>
            <div style='font-weight: bold;'><%--<a href="./read.do?noticeno=${noticeno }&now_page=${param.now_page == null ? 1 : param.now_page }"> --%>${ntitle }</div>
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
                <A href="/fboard/delete.do?noticeno=${noticeno}&now_page=${param.now_page == null ? 1 : param.now_page}" title="삭제"><IMG src="/notice/images/delete.png" class="icon"></A>
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

