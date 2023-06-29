<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="fboardno" value="${fboardVO.fboardno }" />
<c:set var="ftitle" value="${fboardVO.ftitle }" />        
<c:set var="fcontent" value="${fboardVO.fcontent }" />
<c:set var="file1" value="${fboardVO.file1 }" />
<c:set var="file1saved" value="${fboardVO.file1saved }" />
<c:set var="thumb1" value="${fboardVO.thumb1 }" />
<c:set var="youtube" value="${fboardVO.youtube }" />
<c:set var="word" value="${fboardVO.word }" />
<c:set var="views" value="${fboardVO.views }" />
<c:set var="replycnt" value="${fboardVO.replycnt }" />
<c:set var="size1_label" value="${fboardVO.size1_label }" />
<c:set var="rdate" value="${fboardVO.rdate.substring(0, 16) }" />

<!DOCTYPE html> 
<html lang="ko"> 
<head> 
<meta charset="UTF-8"> 
<meta name="viewport" content="user-scalable=yes, initial-scale=1.0, maximum-scale=3.0, width=device-width" /> 
<title>자유게시판</title>
<link rel="shortcut icon" href="/images/star.png" />
 
<link href="/css/style.css" rel="Stylesheet" type="text/css">

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

<script type="text/javascript">
let reply_list; // 댓글 목록

 $(function(){
    $('#btn_recom').on("click", function() { update_recom_ajax(${fboardno}); });
    $('#btn_login').on('click', login_ajax);
    $('#btn_loadDefault').on('click', loadDefault);

    // ---------------------------------------- 댓글 관련 시작 ----------------------------------------
    var frm_reply = $('#frm_reply');
    $('#content', frm_reply).on('click', check_login);  // 댓글 작성 시 로그인 여부 확인
    $('#btn_create', frm_reply).on('click', reply_create);  // 댓글 작성 시 로그인 여부 확인

    list_by_fboardno_join(); //댓글 목록

    $('#btn_add').on('click', list_by_fboardno_join_add);  // [더보기] 버튼
    // ---------------------------------------- 댓글 관련 종료 ----------------------------------------
    
  });

 //좋아요
  function update_recom_ajax(fboardno) {
    // console.log('-> fboardno:' + fboardno);
    var params = "";
    // params = $('#frm').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    params = 'fboardno=' + fboardno; // 공백이 값으로 있으면 안됨.
    $.ajax(
      {
        url: '/fboard/update_recom_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          // console.log('-> rdata: '+ rdata);
          var str = '';
          if (rdata.cnt == 1) {
            // console.log('-> btn_recom: ' + $('#btn_recom').val());  // X
            // console.log('-> btn_recom: ' + $('#btn_recom').html());
            $('#btn_recom').html('♥('+rdata.recom+')');
            $('#span_animation').hide();
          } else {
            $('#span_animation').html("지금은 추천을 할 수 없습니다.");
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

    // $('#span_animation').css('text-align', 'center');
    $('#span_animation').html("<img src='/fboard/images/ani04.gif' style='width: 8%;'>");
    $('#span_animation').show(); // 숨겨진 태그의 출력
  }

  function loadDefault() {
    $('#id').val('user1');
    $('#passwd').val('1234');
  } 
  
  <%-- 로그인 --%>
  function login_ajax() {
    var params = "";
    params = $('#frm_login').serialize(); // 직렬화, 폼의 데이터를 키와 값의 구조로 조합
    // params += '&${ _csrf.parameterName }=${ _csrf.token }';
    // console.log(params);
    // return;
    
    $.ajax(
      {
        url: '/member/login_ajax.do',
        type: 'post',  // get, post
        cache: false, // 응답 결과 임시 저장 취소
        async: true,  // true: 비동기 통신
        dataType: 'json', // 응답 형식: json, html, xml...
        data: params,      // 데이터
        success: function(rdata) { // 응답이 온경우
          var str = '';
          console.log('-> login cnt: ' + rdata.cnt);  // 1: 로그인 성공
          
          if (rdata.cnt == 1) {
            // 쇼핑카트에 insert 처리 Ajax 호출
            $('#div_login').hide();
            // alert('로그인 성공');
            $('#login_yn').val('YES'); // 로그인 성공 기록
            cart_ajax_post(); // 쇼핑카트에 insert 처리 Ajax 호출     
            
          } else {
            alert('로그인에 실패했습니다.<br>잠시후 다시 시도해주세요.');
            
          }
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          console.log(error);
        }
      }
    );  //  $.ajax END

  }

  // 댓글 작성시 로그인 여부 확인
  function check_login() {
    var frm_reply = $('#frm_reply');
    if ($('#memberno', frm_reply).val().length == 0 ) {
      $('#modal_title').html('댓글 등록'); // 제목 
      $('#modal_content').html("로그인해야 등록 할 수 있습니다."); // 내용
      $('#modal_panel').modal();            // 다이얼로그 출력
      return false;  // 실행 종료
    }
  }

  // 댓글 등록
  function reply_create() {
    var frm_reply = $('#frm_reply');
    
    if (check_login() !=false) { // 로그인 한 경우만 처리
      var params = frm_reply.serialize(); // 직렬화: 키=값&키=값&...
      // alert(params);
      // return;

      // 자바스크립트: 영숫자, 한글, 공백, 특수문자: 글자수 1로 인식
      // 오라클: 한글 1자가 3바이트임으로 300자로 제한
      // alert('내용 길이: ' + $('#content', frm_reply).val().length);
      // return;
      
      if ($('#content', frm_reply).val().length > 300) {
        $('#modal_content').html("댓글 내용은 300자 이상 입력 할 수 없습니다."); // 내용
        $('#modal_panel').modal();           // 다이얼로그 출력
        return;  // 실행 종료
      }        

      $.ajax({
        url: "../reply/create.do", // action 대상 주소
        type: "post",          // get, post
        cache: false,          // 브러우저의 캐시 영역 사용안함.
        async: true,           // true: 비동기
        dataType: "json",   // 응답 형식: json, xml, html...
        data: params,        // 서버로 전달하는 데이터
        success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
          // alert(rdata);
          var msg = ""; // 메시지 출력
          var tag = ""; // 글목록 생성 태그
          
          if (rdata.cnt > 0) {
            $('#modal_content').attr('class', 'alert alert-success'); // CSS 변경
            msg = "댓글을 등록했습니다.";
            $('#content', frm_reply).val('');
            $('#passwd', frm_reply).val('');

            // list_by_fboardno_join(); // 댓글 목록을 새로 읽어옴
            
            $('#reply_list').html(''); // 댓글 목록 패널 초기화, val(''): 안됨
            $("#reply_list").attr("data-replypage", 1);  // 댓글이 새로 등록됨으로 1로 초기화
            
            // list_by_fboardno_join_add(); // 페이징 댓글, 페이징 문제 있음.
            // alert('댓글 목록 읽기 시작');
            // global_rdata = new Array(); // 댓글을 새로 등록했음으로 배열 초기화
            // global_rdata_cnt = 0; // 목록 출력 글수
            
            // list_by_fboardno_join(); // 페이징 댓글
          } else {
            $('#modal_content').attr('class', 'alert alert-danger'); // CSS 변경
            msg = "댓글 등록에 실패했습니다.";
          }
          
          //$('#modal_title').html('댓글 등록'); // 제목 
          $('#modal_content').html(msg);     // 내용
          $('#modal_panel').modal();           // 다이얼로그 출력
        },
        // Ajax 통신 에러, 응답 코드가 200이 아닌 경우, dataType이 다른경우 
        error: function(request, status, error) { // callback 함수
          var msg = 'ERROR request.status: '+request.status + '/ ' + error;
          console.log(msg); // Chrome에 출력
        }
      });
    }
  }

  // fboardno 별 소속된 댓글 목록, 2건만 출력
  function list_by_fboardno_join() {
    var params = 'fboardno=' + ${fboardVO.fboardno };

    $.ajax({
      url: "../reply/list_by_fboardno_join.do", // action 대상 주소
      type: "get",           // get, post
      cache: false,          // 브러우저의 캐시 영역 사용 안함.
      async: true,           // true: 비동기
      dataType: "json",   // 응답 형식: json, xml, html...
      data: params,        // 서버로 전달하는 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온 경우
          
        // alert(rdata);
        var msg = '';
        
        $('#reply_list').html(''); // 패널 초기화, val(''): 안됨    

        // -------------------- 전역 변수에 댓글 목록 추가 --------------------
        reply_list = rdata.list;
        // -------------------- 전역 변수에 댓글 목록 추가 --------------------
        // alert('rdata.list.length: ' + rdata.list.length);
        
        var last_index=1; 
        if (rdata.list.length >= 2 ) { // 댓글이 2건 이상이라면 2건만 출력
          last_index = 2;
        }
        
        for (i=0; i < rdata.list.length; i++) {
          var row = rdata.list[i];
          msg += "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
          
          if ('${fboardVO.memberno}' == row.memberno) {           
            //처음 5글자는 그대로 출력하고, 나머지 부분은 * 10개로 표시, 글쓴이 댓글은 파란색으로 출력
            msg += "<span style='font-weight: bold; color: #4431bf;'>" + row.id.substring(0, 5) + "*".repeat(10) + "</span>";
            msg += "  " + row.rdate;
          }else{
             msg += "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
             msg += "<span style='font-weight: bold;'>" + row.id.substring(0, 5) + "*".repeat(10)  + "</span>";
             msg += "  " + row.rdate;
              }
          
          if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치 여부 확인, 본인의 글만 삭제 가능함 ★
            msg += " <A href='javascript:reply_delete("+row.replyno+")'><IMG src='/reply/images/delete.png'></A>";
          }
          
          msg += "  " + "<br>";
          msg += row.content;
          msg += "</DIV>";
        }
        // alert(msg);
        $('#reply_list').append(msg);
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });
    
  }

  //댓글 삭제 레이어 출력
  function reply_delete(replyno) {
    // alert('replyno: ' + replyno);
    var frm_reply_delete = $('#frm_reply_delete');
    $('#replyno', frm_reply_delete).val(replyno); // 삭제할 댓글 번호 저장
    $('#modal_panel_delete').modal();             // 삭제폼 다이얼로그 출력
  }

  // 댓글 삭제 처리
  function reply_delete_proc(replyno) {
    // alert('replyno: ' + replyno);
    var params = $('#frm_reply_delete').serialize();
    $.ajax({
      url: "../reply/delete.do", // action 대상 주소
      type: "post",           // get, post
      cache: false,          // 브러우저의 캐시영역 사용안함.
      async: true,           // true: 비동기
      dataType: "json",   // 응답 형식: json, xml, html...
      data: params,        // 서버로 전달하는 데이터
      success: function(rdata) { // 서버로부터 성공적으로 응답이 온경우
        // alert(rdata);
        var msg = "";
        
        if (rdata.passwd_cnt ==1) { // 패스워드 일치
          if (rdata.delete_cnt == 1) { // 삭제 성공

            $('#btn_frm_reply_delete_close').trigger("click"); // 삭제폼 닫기, click 발생 
            
            $('#' + replyno).remove(); // 태그 삭제
              
            return; // 함수 실행 종료
          } else {  // 삭제 실패
            msg = "패스워드는 일치하나, 댓글 삭제에 실패했습니다. <br>";
            msg += " 다시 한번 시도해주세요."
          }
        } else { // 패스워드 일치하지 않음.
          // alert('패스워드 불일치');
          // return;
          
          msg = "패스워드가 일치하지 않습니다.";
          $('#modal_panel_delete_msg').html(msg);

          $('#passwd', '#frm_reply_delete').focus();  // frm_reply_delete 폼의 passwd 태그로 focus 설정
          
        }
      },
      // Ajax 통신 에러, 응답 코드가 200이 아닌경우, dataType이 다른경우 
      error: function(request, status, error) { // callback 함수
        console.log(error);
      }
    });
  }

  // [더보기] 버튼 처리
  function list_by_fboard_join_add() {
    // alert('list_by_fboard_join_add called');
    
    let cnt_per_page = 2; // 2건씩 추가
    let replyPage=parseInt($("#reply_list").attr("data-replyPage"))+cnt_per_page; // 2
    $("#reply_list").attr("data-replyPage", replyPage); // 2
    
    var last_index=replyPage + 2; // 4
    // alert('replyPage: ' + replyPage);
    
    var msg = '';
    for (i=replyPage; i < last_index; i++) {
      var row = reply_list[i];
      
      if ('${fboardVO.memberno}' == row.memberno) {
          msg += "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
          //처음 5글자는 그대로 출력하고, 나머지 부분은 * 10개로 표시, 글쓴이 댓글은 파란색으로 출력
          msg += "<span style='font-weight: bold; color: #4431bf;'>" + row.id.substring(0, 5) + "*".repeat(10) + "</span>";
          msg += "  " + row.rdate;
        }else{
           msg += "<DIV id='"+row.replyno+"' style='border-bottom: solid 1px #EEEEEE; margin-bottom: 10px;'>";
           msg += "<span style='font-weight: bold;'>" + row.id.substring(0, 5) + "*".repeat(10)  + "</span>";
           msg += "  " + row.rdate;
            }

      if ('${sessionScope.memberno}' == row.memberno) { // 글쓴이 일치여부 확인, 본인의 글만 삭제 가능함 ★
          msg += " <A href='javascript:reply_delete("+row.replyno+")'><IMG src='/reply/images/delete.png'></A>";
        }
        msg += "  " + "<br>";
        msg += row.content;
        msg += "</DIV>";

      // alert('msg: ' + msg);
      $('#reply_list').append(msg);
    }    
  }

response.setHeader("Set-Cookie", "cookieName=; Max-Age=0");

</script> 
    
</head> 
 
<body>
<c:import url="/menu/top.do" />

<!-- Modal 알림창 시작 -->
<div class="modal fade" id="modal_panel" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
      <h4 class="modal-title" id='modal_title'></h4><!-- 제목 -->
        <button type="button" class="close" data-dismiss="modal">×</button>
      </div>
      <div class="modal-body">
        <p id='modal_content'></p>  <!-- 내용 -->
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div> <!-- Modal 알림창 종료 -->

<!-- -------------------- 댓글 삭제폼 시작 -------------------- -->
<div class="modal fade" id="modal_panel_delete" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
      <h4 class="modal-title">댓글 삭제</h4><!-- 제목 -->
        <button type="button" class="close" data-dismiss="modal">×</button>
      </div>
      <div class="modal-body">
        <form name='frm_reply_delete' id='frm_reply_delete'>
          <input type='hidden' name='replyno' id='replyno' value=''>
          
          <label>패스워드</label>
          <input type='password' name='passwd' id='passwd' class='form-control'>
          <DIV id='modal_panel_delete_msg' style='color: #AA0000; font-size: 1.1em;'></DIV>
        </form>
      </div>
      <div class="modal-footer">
        <button type='button' class='btn btn-danger' 
                     onclick="reply_delete_proc(frm_reply_delete.replyno.value); frm_reply_delete.passwd.value='';">삭제</button>

        <button type="button" class="btn btn-default" data-dismiss="modal" 
                     id='btn_frm_reply_delete_close'>Close</button>
      </div>
    </div>
  </div>
</div>
<!-- -------------------- 댓글 삭제폼 종료 -------------------- -->

<DIV class='title_line'>
자유게시판
</DIV>

<DIV class='content_body'>

  <ASIDE class="aside_right">
     <A href="./update.do?fboardno=${fboardno}&now_page=${param.now_page}">수정</A>
      <span class='menu_divide' >│</span>
      <A href="./update_file.do?fboardno=${fboardno}&now_page=${param.now_page}">파일</A>  
      <span class='menu_divide' >│</span>
      <A href="./delete.do?fboardno=${fboardno}&now_page=${param.now_page}">삭제</A>  
    <span class='menu_divide' >│</span>  

    <A href="javascript:location.reload();">새로고침</A>
  
  </ASIDE> 
  
    <DIV style="text-align: right; clear: both;">  
    <form name='frm' id='frm' method='get' action='./list_all.do'>
      
      <c:choose>
        <c:when test="${param.word != '' }"> <%-- 검색하는 경우 --%>
          <input type='text' name='word' id='word' value='${param.word }' class='input_word'>
        </c:when>
        <c:otherwise> <%-- 검색하지 않는 경우 --%>
          <input type='text' name='word' id='word' value='' class='input_word'>
        </c:otherwise>
      </c:choose>
      <button type='submit' class='btn btn-info btn-sm'>검색</button>
      <c:if test="${param.word.length() > 0 }">
        <button type='button' class='btn btn-info btn-sm' 
                    onclick="location.href='./list_all.do?word='">검색 취소</button>  
      </c:if>    
    </form>
  </DIV>
    
  </DIV>
  
  <DIV class='menu_line'></DIV>

  <fieldset class="fieldset_basic">
    <ul style='background-color: #F6F6F6;'>
      <li class="li_none">
        <DIV style="width: 100%; word-break: break-all; text-align: center;" >  
          <span style="font-size: 1.5em; font-weight: bold; text-align: center;">${ftitle }</span><br><br>
          <div style="font-size: 1em; text-align: right; width: 89%;"><img src="/member/images/user.png" style="height: 16px"> ${mname} ${rdate }</div>
          <div style="font-size: 1em; text-align: right; width: 89%;">조회수: <span id="views">${views}</span></div>
          <br><br>
        </DIV>

      <DIV style="width: 100%; word-break: break-all; text-align: center;">  
                <c:choose>
            <c:when test="${thumb1.endsWith('jpg') || thumb1.endsWith('png') || thumb1.endsWith('gif')}">
              <%-- /static/fboard/storage/ --%>
              <img src="/fboard/storage/${file1saved }" style='width: 40%; float: center; margin-top: 0.5%; margin-right: 1%;'> 
            </c:when>
            <c:otherwise> <!-- 기본 이미지 출력 -->
              <%-- <img src="/fboard/images/logo2.gif" style='width: 30%; float: center; margin-top: 0.5%; margin-right: 1%;'> --%>
            </c:otherwise>
          </c:choose>
          
          <br><br>
          <div style="width: 80%; font-size: 1.1em; text-align: left; margin-left: 10%;">${fcontent }</div>
          </DIV>
           <br>
            <br>
           </li>

      <c:if test="${youtube.trim().length() > 0 }">
        <li class="li_none" style="clear: both; padding-top: 5px; padding-bottom: 5px;">
          <DIV style="text-align: center;">
            ${youtube }
          </DIV>
        </li>
      </c:if>
        <DIV style="width: 80%; text-align: left; margin-left: 10%;" >
         <c:if test="${file1.trim().length() > 0 }"> <%-- ServletRegister.java: registrationBean.addUrlMappings("/download"); --%>
         첨부 파일: <a href='/download?dir=/fboard/storage&filename=${file1saved}&downname=${file1}' > ${file1}</a> (${size1_label})  
          </c:if>
        </DIV>
       <br>
       
    </ul>
    <div style="width: 85%; text-align: right; margin-left: 15%;">  
    <button type="button" onclick="location.href='/fboard/list_all.do'" class="btn btn-info btn-sm">목록형</button>
    <button type="button" onclick="location.href='/fboard/list_grid.do'" class="btn btn-info btn-sm">앨범형</button>
     </div>
     
  </fieldset>



  <!-- ------------------------------ 댓글 영역 시작 ------------------------------ -->
  <DIV style='width: 80%; margin: 0px auto;'>
      <HR>
      <FORM name='frm_reply' id='frm_reply'> <%-- 댓글 등록 폼 --%>
       <img src="/fboard/images/reply.png" style='width: 2%; float: center; float-bottom: 2%, margin-right: 0.2%;'> ${replycnt }
       <br>
          <input type='hidden' name='fboardno' id='fboardno' value='${fboardno}'>
          <input type='hidden' name='memberno' id='memberno' value='${sessionScope.memberno}'>
          
          <textarea name='content' id='content' style='width: 100%; height: 60px;' placeholder="댓글 작성, 로그인해야 등록 할 수 있습니다."></textarea>
          <input type='password' name='passwd' id='passwd' placeholder="패스워드">
          <button type='button' id='btn_create'>등록</button>
      </FORM>
      <HR>
      <DIV id='reply_list' data-replyPage='0'></DIV><%-- 댓글 목록 --%>
      

      <DIV id='reply_list_btn' style='border: solid 1px #EEEEEE; margin: 0px auto; width: 100%; background-color: #EEFFFF;'>
          <button id='btn_add' style='width: 100%;'>더보기 ▽</button>
      </DIV> 
    
  </DIV>
  
  <!-- ------------------------------ 댓글 영역 종료 ------------------------------  -->
<jsp:include page="../menu/bottom.jsp" flush='false' />
</body>
 
</html>