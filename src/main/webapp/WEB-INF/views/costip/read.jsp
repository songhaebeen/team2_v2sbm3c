<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="costipno" value="${costipVO.costipno }" />
<c:set var="memberno" value="${costipVO.memberno }" />
<c:set var="title" value="${costipVO.title }" />        
<c:set var="file1" value="${costipVO.file1 }" />
<c:set var="file1saved" value="${costipVO.file1saved }" />
<c:set var="thumb1" value="${costipVO.thumb1 }" />
<c:set var="content" value="${costipVO.content }" />
<c:set var="youtube" value="${costipVO.youtube }" />
<c:set var="word" value="${costipVO.word }" />
<c:set var="size1_label" value="${costipVO.size1_label }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>Team2</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head> 
 
<body>
<c:import url="/menu/top.do" />
 
<DIV class='title_line'><A href="./list_by_costipno.do?costipno=${costipno }" class='title_link'>${title }</A></DIV>

<DIV class='content_body'>
  <ASIDE class="aside_right">
    <%-- 회원 로그인해야 메뉴가 출력됨 --%>
    <c:if test="${sessionScope.id != null }">
      <%--
      http://localhost:9093/costip/create.do?costipno=1
      http://localhost:9093/costip/create.do?costipno=2
      http://localhost:9093/costip/create.do?costipno=3
      --%>
      <A href="./create.do?costipno=${costipno }">등록</A>
      <span class='menu_divide' >│</span>
      <A href="./update_text.do?costipno=${costipno}&now_page=${param.now_page}">글 수정</A>
      <span class='menu_divide' >│</span>
      <A href="./update_file.do?costipno=${costipno}&now_page=${param.now_page}">파일 수정</A>  
      <span class='menu_divide' >│</span>
      <A href="./youtube.do?costipno=${costipno}">Youtube</A>
      <span class='menu_divide' >│</span>
      <A href="./delete.do?costipno=${costipno}&now_page=${param.now_page}&costipno=${costipno}">삭제</A>  
      <span class='menu_divide' >│</span>
    </c:if>

    <A href="javascript:location.reload();">새로고침</A>
    <span class='menu_divide' >│</span>    
    <A href="./list_by_costipno.do?costipno=${costipno }&now_page=${param.now_page}&word=${param.word }">기본 목록형</A>    
    <span class='menu_divide' >│</span>
    <A href="./list_by_costipno_grid.do?costipno=${costipno }&now_page=${param.now_page}&word=${param.word }">갤러리형</A>
  </ASIDE> 
  
  <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_by_costipno.do'>
      
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
                    onclick="location.href='./list_by_cateno.do?cateno=${cateVO.cateno}&word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">
        <DIV style="width: 100%;">
          <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/costip/storage/ --%>
              <img src="/costip/storage/${file1saved }" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <img src="/costip/images/none1.png" style='width: 50%; float: left; margin-top: 0.5%; margin-right: 1%;'> 
            </c:otherwise>
          </c:choose>

          <span style="font-size: 1.5em; font-weight: bold;">${title }</span><br>
          ${content }
        </DIV>
      </li>
      
      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
      
      <li class="li_none" style="clear: both;">
        <DIV style='text-decoration: none;'>
          <br>
          검색어(키워드): ${word }
        </DIV>
      </li>
      <li class="li_none">
        <DIV>
          <c:if test="${file1.trim().length() > 0 }">
            첨부 파일: <a href='/download?dir=/costip/storage&filename=${file1saved}&downname=${file1}'>${file1}</a> (${size1_label})  
          </c:if>
        </DIV>
      </li>   
    </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>
