<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="/css/style.css" rel="Stylesheet" type="text/css">
<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<head>
    <meta charset='utf-8'>
    <meta http-equiv='X-UA-Compatible' content='IE=edge'>
    <title>Page Title</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
    <script>
    var listVisible = false;
      var buttonArray = [];
      function buttonchange(button) {
        var color = button.style.color;
        var value = button.value;
        if(color =="lightgray"){
          button.style.color = "black";
        }else{
          button.style.color = "lightgray";
        }
        getAllButtonsInDiv()
      }
      
      function getAllButtonsInDiv() {
          var divElement = document.getElementById("buttondiv"); // 해당 div의 id를 사용하여 요소 선택
          var list  = [];
          if (divElement) {
            var buttons = divElement.getElementsByTagName("button"); // div 안의 모든 button 요소 가져오기
            for (var i = 0; i < buttons.length; i++) {
              if(buttons[i].style.color == "black"){
                  list.push(buttons[i].value)
      
              }
            }
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "/cosme/list_by_type.do", true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onreadystatechange = function () {
              if (xhr.readyState === 4 && xhr.status === 200) {
                console.log("전송 성공");
                var response = xhr.responseText; // 처리된 결과 문자열을 받음
                var div = document.getElementById("grid");
                div.innerHTML = response;
              }else{
                  console.log("전송 실패");
                  } 
            };
            xhr.send(JSON.stringify({ list: list }));
          } else {
            console.log("해당 div 요소를 찾을 수 없습니다.");
          }
        }

      </script>
</head>
<style>
</style>
<body>
  <c:import url="/menu/top.do" />
<div id="buttondiv">
		<c:forEach items="${type_list}" var="type_list">
		<button class="btn_type" onclick="buttonchange(this)" value="${type_list.cosmetypeno}" style="color: lightgray;">${type_list.cosmetypename}</button>
		</c:forEach>
  <!--라디오 버튼 (인기순 등)-->
</div>
<!-- sdf -->
<div id="grid">

</div>
<Br>
<Br>
<Br>
  <jsp:include page="../menu/bottom.jsp" flush='false' />

</body>
</html>