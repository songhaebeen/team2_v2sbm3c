<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="noticeno" value="${noticeVO.noticeno }" />
<c:set var="ntitle" value="${noticeVO.ntitle }" />        
<c:set var="ncontent" value="${noticeVO.ncontent }" />
           
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>삭제</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
    
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    
</head>
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
<DIV class='title_line'> ${ntitle } 삭제</DIV>
  <br>
  
  <ASIDE class="aside_right">
     <A href="./create.do">등록</A>
     <span class='menu_divide' >│</span>
    <A href="./update.do?noticeno=${noticeno}">수정</A> 
     <span class='menu_divide' >│</span>
    <A href="/notice/list_all.do">목록</A>
      <span class='menu_divide' >│</span>
    <A href="javascript:location.reload();">새로고침</A>

 
  </ASIDE> 
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul>
      <li class="li_none">

        <DIV style='text-align: center; width: 100%; float: center;'>
          <span style='font-size: 1.5em;'>${ntitle}</span>
          <br>
          <FORM name='frm' method='POST' action='./delete.do'>
              <input type='hidden' name='noticeno' value='${noticeno}'>
              <br><br>
              <div style='text-align: center; margin: 10px auto;'>
                <span style="color: #FF0000; font-weight: bold;">삭제를 진행 하시겠습니까? 삭제하시면 복구 할 수 없습니다.</span><br><br>
                <br><br>
                <button type = "submit" class="my-btn btn">삭제 진행</button>
                <button type = "button" onclick = "history.back()" class="my-btn btn">취소</button>
              </div>   
          </FORM>
        </DIV>
      </li>
     </ul>
  </fieldset>

</DIV>
 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>