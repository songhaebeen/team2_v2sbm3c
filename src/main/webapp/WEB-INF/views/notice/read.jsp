<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="noticeno" value="${noticeVO.noticeno }" />
<c:set var="ntitle" value="${noticeVO.ntitle }" />        
<c:set var="ncontent" value="${noticeVO.ncontent }" />
<c:set var="file1" value="${noticeVO.file1 }" />
<c:set var="file1saved" value="${noticeVO.file1saved }" />
<c:set var="thumb1" value="${noticeVO.thumb1 }" />
<c:set var="youtube" value="${noticeVO.youtube }" />
<c:set var="views" value="${noticeVO.views }" />
<c:set var="size1_label" value="${noticeVO.size1_label }" />
<c:set var="rdate" value="${noticeVO.rdate.substring(0, 16) }" />

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>공지사항</title>
<link rel="shortcut icon" href="/images/check.png" />
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
    
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>공지사항</DIV>

<DIV class='content_body'>

  <ASIDE class="aside_right">
    <%-- 관리자로 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.admin_id != null }">
      <%--
      http://localhost:9093/notice/create.do?noticeno=1
      http://localhost:9093/notice/create.do?noticeno=2
      http://localhost:9093/notice/create.do?noticeno=3
      --%>

     <A href="./update.do?noticeno=${noticeno}&now_page=${param.now_page}">글 수정</A>
      <span class='menu_divide' >│</span>
      <A href="./youtube.do?noticeno=${noticeno}">Youtube</A>
      <span class='menu_divide' >│</span>
      <A href="./delete.do?noticeno=${noticeno}&now_page=${param.now_page}">삭제</A>  
    <span class='menu_divide' >│</span>  
    </c:if>
    <A href="javascript:location.reload();">새로고침</A>
  
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul style='background-color: #F6F6F6;'>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all; text-align: center;">
          <span style="font-size: 1.5em; font-weight: bold;">${ntitle }</span><br>
          <div style="font-size: 1em; text-align: right; width: 89%;"><img src="/admin/images/user.png" style="height: 16px"> ${mname } ${rdate }</div>
          <div style="font-size: 1em; text-align: right; width: 89%;">조회수: <span id="views">${views}</span></div>
          <br><br>
          </DIV>
          
         <DIV style="width: 100%; word-break: break-all; text-align: center;">  
         <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/notice/storage/ --%>
              <img src="/notice/storage/${file1saved }" style='width: 30%; float: center; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <%-- <img src="/notice/images/logo2.gif" style='width: 30%; float: center; margin-top: 0.5%; margin-right: 1%;'> --%>
            </c:otherwise>
          </c:choose>
          
         <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
          
           <br><br>
          <div style="width: 80%; font-size: 1.1em; text-align: left; margin-left: 10%;">${ncontent }</div>
          <br>
          <br>
        </DIV>
      </li>
      
      <li class="li_none">
        <DIV style="width: 80%; text-align: left; margin-left: 10%;" >
          <c:if test="${file1.trim().length() > 0 }"> <%-- ServletRegister.java: registrationBean.addUrlMappings("/download"); --%>
            첨부 파일: <a href='/download?dir=/notice/storage&filename=${file1saved}&downname=${file1}' > ${file1}</a> (${size1_label})  
          </c:if>
        </DIV>
        <br>
      </li> 

    </ul>
        <div style="width: 85%; text-align: right; margin-left: 15%;"> 
        <button type="button" onclick="location.href='/notice/list_all.do'" class="btn btn-info btn-sm">목록</button>
    </div>
  </fieldset>
  
 



</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>