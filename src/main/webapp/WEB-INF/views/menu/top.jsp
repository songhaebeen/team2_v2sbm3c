<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
function recommend() {

    var url = 'http://43.201.58.46:8000/ais/recommend_form/?memberno=${sessionScope.memberno }';
    var win = window.open(url, '공지 사항', 'width=1300px, height=850px');
    
    var x = (screen.width - 1300) / 2;
    var y = (screen.height - 850) / 2;
    
    win.moveTo(x, y); // 화면 중앙으로 이동
}
</script>

<DIV class='container_main'> 
    <!-- 헤더 start -->
    <div class="header">
      <nav class="navbar navbar-expand-md navbar-dark fixed-top bg-dark">
            <a class="navbar-brand" href="/">Team2</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarCollapse" aria-controls="navbarCollapse" aria-expanded="false" aria-label="Toggle Navigation">
              <span class="navbar-toggler-icon"></span>
            </button>    
            
                  <c:forEach var="catecoVO" items="${list}">
                    <c:set var="catecono" value="${catecoVO.catecono }" />
                    <c:set var="name" value="${catecoVO.name }" />
                    <li class="nav-item"> 
                    <a class="nav-link" href="/contentsco/list_by_catecono.do?catecono=${catecono }&now_page = 1" class="menu_link">${name }</a>   
                  </c:forEach>
                  </li>
                                    
                  <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                   <a class="nav-link" href="/notice/list_all.do">공지사항</a>
                   </li>
                  
                  <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                    <a class="nav-link" href="/fboard/list_all.do">자유게시판</a>
                  </li>
                                          
                  <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
                      <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">화장품</a>
                      <div class="dropdown-menu">
                          <a class="dropdown-item" href="javascript: recommend();">화장품 추천</a>
                          <a class="dropdown-item" href="/cosme/create.do">화장품 등록</a>
                          <a class="dropdown-item" href="/cosme/cosme_by_cate.do">종류별 리스트</a>
                          <a class="dropdown-item" href="/cosme/list_by_typet.do">타입별 리스트</a>
                          <a class="dropdown-item" href="#">성분별 리스트</a>
                      </div>
                  </li>
                  <li class="nav-item"> <%-- 서브 메뉴가 없는 독립메뉴 --%>
                      <c:choose>
                          <c:when test="${sessionScope.id == null}">
                              <a class="nav-link" href="/member/login.do">로그인</a>
                          </c:when>
                          <c:otherwise>
                              <a class="nav-link" href='/member/logout.do'>${sessionScope.id } 로그아웃</a>
                          </c:otherwise>
                      </c:choose>
                  </li>
                
                  <li class="nav-item dropdown"> <%-- 회원 서브 메뉴 --%>
                      <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">회원</a>
                      <div class="dropdown-menu">
                          <A class='dropdown-item'  href='/good/list_memberno.do'>내가 좋아요한 글</A> 
                          <a class="dropdown-item" href="/reply/list_memberno.do">내가 쓴 댓글</a>
                          <a class="dropdown-item" href="/member/create.do">회원 가입</a>
                          <a class="dropdown-item" href="/member/read.do?memberno=${memberno }">가입 정보</a>
                          <a class="dropdown-item" href="/mail/form_file.do">아이디 찾기</a>
                          <a class="dropdown-item" href="#">비밀번호 찾기</a>                      
                          <a class="dropdown-item" href="/member/passwd_update.do?memberno=${memberno }">비밀번호 변경</a> 
                          <a class="dropdown-item" href="/member/read.do">회원 정보 수정</a>
                          <a class="dropdown-item" href="/member/user_out.do?memberno=${memberno }">회원 탈퇴</a>
                      </div>
                  </li>
              
                  <c:choose>
                    <c:when test="${sessionScope.admin_id == null }">
                      <li class="nav-item">
                        <a class="nav-link" href="/admin/login.do">관리자 로그인</a>
                      </li>
                    </c:when>
                    <c:otherwise>
                      <li class="nav-item dropdown"> <%-- 관리자 서브 메뉴 --%>
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">관리자</a>
                        <div class="dropdown-menu">
                          <a class="dropdown-item" href='/admin/create.do'>관리자 가입</a>
                          <a class="dropdown-item" href='/admin/list.do'>관리자 목록</a>
                          <a class="dropdown-item" href='/member/list.do'>회원 목록</a>
                          <A class='dropdown-item'  href='/good/list_all.do'>좋아요 목록</A> 
                           <A class='dropdown-item'  href='/reply/list_join.do'>댓글 목록</A>
                           <a class="dropdown-item" href='/cate/list_all.do'>카테고리 전체 목록</a>
                          <a class="dropdown-item" href='/admin/logout.do'>관리자 ${sessionScope.admin_id } 로그아웃</a>
                        </div>
                      </li>
                   <li class="nav-item dropdown"> <%-- 화장품 목록 서브 메뉴 --%>
                      <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#">화장품 목록</a>
                      <div class="dropdown-menu">
                          <a class="dropdown-item" href="/cosme_cate/list_all.do">화장품 종류</a>
                          <a class="dropdown-item" href="/cosmetype/list_all.do">화장품 타입</a>
                          <a class="dropdown-item" href="/ingred/list_all.do">화장품 성분</a>
                      </div>
                  </li>
                    </c:otherwise>
                  </c:choose>     
            </div>    
        </nav>

    </div>
    <!-- 헤더 end -->
    
    <%-- 내용 --%> 
    <DIV class='content'>
      <div style='clear: both; height: 50px;'></div>
      