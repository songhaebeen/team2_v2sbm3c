<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="replyno" value="${replyMemberVO.replyno}" />
<c:set var="fboardno" value="${replyMemberVO.fboardno}" />
<c:set var="memberno" value="${replyMemberVO.memberno}" />
<c:set var="content" value="${replyMemberVO.content}" />

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" />
<title>댓글 수정</title>

<link href="/css/style.css" rel="Stylesheet" type="text/css">

<script type="text/JavaScript" src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

</head>

<body>
<c:import url="/menu/top.do" />

<DIV class='title_line'>수정</DIV>

<DIV class='content_body'>
    <ASIDE class='aside_right'>
    <A href="javascript:history.back();">돌아가기</A>
  </ASIDE>

  <DIV class='menu_line'></DIV>

  <FORM name='frm' method='POST' action='./update.do'>
    <input type="hidden" name="replyno" value="${param.replyno}">
    <div>
       <label>내용</label>
       <textarea name='content' required="required" class="form-control" rows="4" style='width: 80%;'>${content }</textarea>
    </div>
    <div>
      <label>패스워드</label>
      <input type='password' name='passwd' value='1234' required="required" class="form-control" style='width: 30%;'>
    </div>

    <div class="content_body_bottom">
      <button type="submit" class="btn btn-primary">저장</button>
      <button type="button" onclick="history.back()" class="btn btn-primary">취소</button>
    </div>

  </FORM>
</DIV>

<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>

</html>
