<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="dev.mvc.cosme_cate.Cosme_cateVO" %>

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
                console.log("받은 결과 문자열: " + response);
                var div = document.getElementById("panel");
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
  <style>
    .btn_type {
      color: lightgray;
    }
    .active {
      color: black;
    }
  </style>
  <script>
    function buttonchange(button) {
      if (!button.classList.contains('active')) {
        var buttons = document.getElementsByClassName('btn_type');
        for (var i = 0; i < buttons.length; i++) {
          buttons[i].classList.remove('active');
          buttons[i].style.color = 'lightgray';
        }
        button.classList.add('active');
        button.style.color = 'black';
      }
    }
  </script>
</head>
<body>
<%
Cosme_cateVO cosme_cateVO = new Cosme_cateVO(); 
%>
<div id="buttondiv">
  <button class="btn_type" onclick="buttonchange(this)" value="1" style="color: lightgray;">수분/진정</button>
  <button class="btn_type" onclick="buttonchange(this)" value="2" style="color: lightgray;">수딩/미백</button>
  <button class="btn_type" onclick="buttonchange(this)" value="3" style="color: lightgray;">주름 개선</button>
  <button class="btn_type" onclick="buttonchange(this)" value="4" style="color: lightgray;">열감</button>
  <!--라디오 버튼 (인기순 등)-->
</div>
<h1 id="panel">여기랑게</h1>
<script>
function buttonchange(button) {
  var cosme_cateno = button.value; 
  var link = "list_by_cate.do?cosme_cateno=" + cosme_cateno;
  window.location.href = link; 
}
</script>

</body>
</html>
<!-- sdf -->
<div id="grid">
  <div class="product-grid">
    <div class="product-item">
      <img class="img-90" src="" alt="상품 1 이미지">
      <h3>상품 1</h3>
      <p>상품 1 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="" alt="상품 2 이미지">
      <h3>상품 2</h3>
      <p>상품 2 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 3 이미지">
      <h3>상품 3</h3>
      <p>상품 3 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 3 이미지">
      <h3>상품 3</h3>
      <p>상품 3 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 3 이미지">
      <h3>상품 3</h3>
      <p>상품 3 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 3 이미지">
      <h3>상품 3</h3>
      <p>상품 3 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="" alt="상품 4이미지">
      <h3>상품 4</h3>
      <p>상품 4 설명</p>
    </div>
</div>
</div>
<Br>
<Br>
<Br>
  <jsp:include page="../menu/bottom.jsp" flush='false' />

</body>
</html>