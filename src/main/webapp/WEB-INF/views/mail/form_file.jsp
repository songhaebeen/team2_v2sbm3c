<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<% String root = request.getContextPath(); // context 추출 %>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메일 쓰기 http://localhost:9093/mail/form.do</title>
<link href="../css/style.css" rel="Stylesheet" type="text/css">

</head>
 
<body>
<c:import url="/menu/top.do" />
 
<div style='width: 80%; margin: 0px auto;'>
<div class='title_line'>mail 쓰기</div>
<form name='mailForm' method='post' action="./send_file.do" enctype="multipart/form-data">
  <table class='table_basic'>
    <colgroup>
      <col style='width: 20%;' />   <!-- 출력 순서 -->
      <col style='width: 80%;' /> <!-- 제목 -->
    </colgroup>
    <tr>
      <th class='th_basic'>받는 사람</th>
      <td class='td_left'><input type="text" name="receiver" value='testcell2014@studydesk.co.kr' class='input_basic' style='width: 50%;'></td>
    </tr>
    <tr>
      <th class='th_basic'>보내는 사람</th>
      <td class='td_left'><input type="text" name="from" value='testcell2014@gmail.com' class='input_basic' style='width: 90%;'></td>
  </tr>
  <tr>
    <th class='th_basic'>제 목</th>
    <td class='td_left'><input type="text" name="title" value="인증 메일을 보냅니다." class='input_basic' style='width: 90%;'></td>
  </tr>
  <tr>
    <th class='th_basic'>첨부 파일</th>
    <td class='td_left'><input type="file" name="file1MF" class='input_basic' style='width: 90%;' multiple="multiple"></td>
  </tr>
  <tr>
    <td class='td_left' colspan="2">
      <textarea name="content" rows="15"  style='width: 100%; border: #AAAAAA 1px solid;'>오늘 저녁에 많은 눈 예상</textarea>
    </td>
  </tr>
</table>
 
<div  class="bottom_menu">
  <input type="submit" value="보내기">
  <input type="button" value="취소" onclick="history.back()">
</div>
</form>
</div>
 
</body>
<jsp:include page="../menu/bottom.jsp" flush='false' />
