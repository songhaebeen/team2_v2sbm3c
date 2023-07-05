<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>단 댓글 목록</title>
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">
 
<script type="text/JavaScript"
          src="http://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
 
</head>
 
<body>
<c:import url="/menu/top.do" />
   <DIV class="title_line">
    회원 별 댓글 목록
  </DIV>
    <ASIDE class='aside_right'> 
    <A href="javascript:history.back();">돌아가기</A>
  </ASIDE>
  
  <div class='menu_line'></div>
  
  <div style='width: 100%;'>
    <table class="table table-striped" style='width: 100%;'>
      <colgroup>
        <col style="width: 10%;"></col>
        <col style="width: 10%;"></col>
        <col style="width: 10%;"></col>
        <col style="width: 25%;"></col>
        <col style="width: 25%;"></col>
         <col style="width: 10%;"></col>
        <col style="width: 10%;"></col>
        
      </colgroup>
      <%-- table 컬럼 --%>
      <thead>
        <tr>
          <th style='text-align: center;'>댓글 번호</th>
          <th style='text-align: center;'>글 번호</th>
          <th style='text-align: center;'>회원 번호</th>
          <th style='text-align: center;'>글 제목</th>
          <th style='text-align: left;'>내용</th>
          <th style='text-align: center;'>등록일</th>
          <th style='text-align: center;'>기타</th>
        </tr>
      
      </thead>
      
      <%-- table 내용 --%>
      <tbody>
        <c:forEach var="replyMemberVO" items="${list }">
          <c:set var="replyno" value="${replyMemberVO.replyno }" />
          <c:set var="fboardno" value="${replyMemberVO.fboardno }" />
          <c:set var="memberno" value="${replyMemberVO.memberno }" />
          <c:set var="ftitle" value="${replyMemberVO.ftitle }" />
          <c:set var="content" value="${replyMemberVO.content }" />
          <c:set var="rdate" value="${replyMemberVO.rdate }" />
          
          <tr style='height: 50px;'> 
            <td style='text-align: center; vertical-align: middle;'>
              ${replyno }
            </td> 
            <td style='text-align: center; vertical-align: middle;'>
              <A href='../fboard/read.do?fboardno=${fboardno }'>${fboardno}</A>
            </td>
            <td style='text-align: center; vertical-align: middle;'>
              <A href='../member/read.do?memberno=${memberno }'><span> ${memberno }</span></A>
            </td>
              <td style='text-align: center; vertical-align: middle;'>
             ${ftitle }
            </td>
            <td style='text-align: left; vertical-align: middle;'>${content}</td>
            <td style='text-align: center; vertical-align: middle;'>
              ${rdate.substring(0,10)}
            </td>
            <td style='text-align: center; vertical-align: middle;'>
            <a href="./update.do?replyno=${replyno}"><img src="/reply/images/update.png" title="수정"  border='0' /></a>
              <a href="./delete.do?fboardno=${fboardno}&replyno=${replyno}"><img src="/reply/images/delete.png" title="삭제"  border='0' /></a>
            </td>
          </tr>
        </c:forEach>
        
      </tbody>
    </table>
    <br><br>
  </div>
 
<jsp:include page="../menu/bottom.jsp" />
</body>
 
</html>
