<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="dev.mvc.cateco.CatecoVO" %>

<DIV class='container_main'> 
  <%-- 화면 상단 메뉴 --%>
  <DIV class='top_img'>
    <DIV class='top_menu_label'>Resort 2.0 영화와 여행이있는 리조트</DIV>
    <NAV class='top_menu'>
      <A class='menu_link'  href='/' >힐링 리조트</A><span class='top_menu_sep'> </span>
      
      <%
              // 레코드가 없어도 list는 null 아님
              ArrayList<CatecoVO> list = (ArrayList<CatecoVO>)request.getAttribute("list");
              for (int i=0; i < list.size(); i++) {
                CatecoVO cateVO = list.get(i);
            %>
        <A href="#" class="menu_link"><%=cateVO.getName() %></A><span class='top_menu_sep'> </span>
      <%  
      }
      %>
      
      <A href="/contents/list_all.do" class="menu_link">전체 글 목록</A><span class='top_menu_sep'> </span>
      
      <%
      String admin_id = (String)session.getAttribute("admin_id");

      if (admin_id == null) { // 로그인 안된 경우
      %>
        <a href="/admin/login.do" class="menu_link">관리자 로그인</a><span class='top_menu_sep'> </span>
      <%  
      } else { // 로그인 한 경우
      %>
        <A class='menu_link'  href='/cate/list_all.do'>카테고리 전체 목록</A><span class='top_menu_sep'> </span>
        
        <a href="/admin/logout.do" class="menu_link">관리자 <%=admin_id %> 로그아웃</a><span class='top_menu_sep'> </span>
      <%  
      }
      %>
            
    </NAV>
  </DIV>
  
  <%-- 내용 --%> 
  <DIV class='content'>
  
  