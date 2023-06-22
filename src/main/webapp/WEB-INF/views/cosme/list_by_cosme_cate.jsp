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
    <title>종류별 추천</title>
    <meta name='viewport' content='width=device-width, initial-scale=1'>
<script>
    function sendTypevalue(button) {
        var divElement = document.getElementById("buttondiv");
        var value = button.value;
        if (divElement) {
            var buttons = divElement.getElementsByTagName("button"); // div 안의 모든 button 요소 가져오기
            for (var i = 0; i < buttons.length; i++) {
                if (buttons[i] == button) {
                    console.log(value);
                    buttons[i].style.color = "black";
                    buttons[i].disabled = true;
                } else {
                    buttons[i].style.color = "lightgray";
                    buttons[i].disabled = false;
                }

                var xhr = new XMLHttpRequest();
                xhr.open("POST", "/cosme/cosme_by_cate.do", true);
                xhr.setRequestHeader("Content-Type", "application/json");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        console.log("전송 성공");
                        var response = xhr.responseText; // 처리된 결과 문자열을 받음
                        var div = document.getElementById("grid");
                        div.innerHTML = response;
                    } else {
                        console.log("전송 실패");
                    }
                };
            }
                xhr.send(JSON.stringify({ value: value }));
        }
    }
</script>
</head>
<style>
</style>
<body>
  <c:import url="/menu/top.do" />

<div id="buttondiv">
        <c:forEach items="${list}" var="list">
        <button class="btn_type" onclick="sendTypevalue(this)" value="${list.cosme_cateno}" style="color: lightgray;">${list.cosme_catename}</button>
        </c:forEach>
  <!--라디오 버튼 (인기순 등)-->
</div>


<!-- sdf -->
<div>
  <div id="grid" class="product-grid">
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 1 이미지">
      <h3>상품 1</h3>
      <p>상품 1 설명</p>
    </div>
    <div class="product-item">
      <img class="img-90" src="/images/logo2.gif" alt="상품 2 이미지">
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
      <img class="img-90" src="/images/logo2.gif" alt="상품 4이미지">
      <h3>상품 4</h3>
      <p>상품 4 설명</p>
    </div>
</div>
</div>
<Br>
<Br>
<Br>
    <jsp:include page="../menu/bottom.jsp" flush='false' />s

</body>
</html>