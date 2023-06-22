<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="cosmeno" value="${cosmeVO.cosmeno }" />
<c:set var="brand" value="${cosmeVO.brand }" />
<c:set var="cosmename" value="${cosmeVO.cosmename }" />
<c:set var="rdate" value="${cosmeVO.rdate }" />
<c:set var="masterno" value="${cosmeVO.masterno }" />
<c:set var="cosme_cateno" value="${cosmeVO.cosme_cateno }" />
<c:set var="cosme_youtube" value="${cosmeVO.cosme_youtube }" />
 
<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=5.0, width=device-width" /> 
<title>화장품 수정</title>
<link rel="shortcut icon" href="/images/star.png" /> <%-- /static 기준 --%>
<link href="/css/css/style.css" rel="Stylesheet" type="text/css"> <!-- /static 기준 -->
 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<DIV class='content_body'>
  <DIV class='title_line'>화장품 수정</DIV>
  
  <FORM name='frm' method='POST' action='/cosme/update.do' enctype="multipart/form-data">
    <input type="hidden" name="cosmeno" value="${param.cosmeno }">
    
    <div>
       <label class="">제목</label>
       <input type='text' name='cosmename' value='${cosmename }' required="required" 
                 autofocus="autofocus" class="form-control" style='width: 100%;'>
    </div>
    <br><Br>
    <div>
       <label class="">브랜드</label>
       <input type='text' name='brand' value='${brand }' required="required" 
                 class="form-control" style='width: 100%;'>
    </div>
    <div>
       <label class="">화장품 관련 유튜브 채널</label>
       <input type='text' name='cosme_youtube' value='${cosme_youtube }' 
                 class="form-control" style='width: 100%;'>
    </div>
    <div>
        <label>화장품 카테고리</label>
        <select name='cosme_cateno' >
          <c:forEach var="cosme_cateVO" items="${list2}" >
            <option value="${cosme_cateVO.cosme_cateno}">${cosme_cateVO.cosme_catename}</option>
          </c:forEach>
        </select>
    </div>
    <div>
        <label>화장품 타입</label>
        <c:forEach items="${coseme_type_list}" var="CosmetypeVO">
            <input type="checkbox" name="cosmetype" value="${CosmetypeVO.cosmetypeno}" id="${CosmetypeVO.cosmetypeno}" />
            <label for="cosmetype_${CosmetypeVO.cosmetypeno}">${CosmetypeVO.cosmetypename}</label>
        </c:forEach>
    </div>
    
    <div>
        <label>화장품 성분</label>
        <c:forEach items="${ingred_list}" var="IngredVO">
            <input type="checkbox" name="ingred" value="${IngredVO.ingredno}" id="ingred_${IngredVO.ingredno}" />
            <label for="ingred_${IngredVO.ingredno}">${IngredVO.ingredname}</label>
        </c:forEach>
    </div>

    <div class="content_body_bottom">
      <button type="submit" class="btn btn-primary">수정</button>
    </div>
  
  </FORM>
</DIV>
 

 
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>