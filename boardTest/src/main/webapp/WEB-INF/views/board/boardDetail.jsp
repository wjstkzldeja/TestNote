<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="name" var="secName"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>게시판</title>
<!--네비시작-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--네비끝-->
<style type="text/css">
body {
	text-align: center;
	margin: 0px auto;
}
#div1{margin:0px auto;width:30%;}
#btitle{border-bottom: 1px solid gray; text-align:left;padding-bottom: 5px;}
#bauthor{border-bottom: 1px solid gray; text-align:left;padding-bottom: 5px;}
#bemail{border-bottom: 1px solid gray; text-align:left;padding-bottom: 5px;}
#bcontent{border-bottom: 1px solid gray; text-align:left;padding-bottom: 5px;}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
function del(num) {
	if(confirm('정말 삭제하시겠어요?')){
		var deleteObj ={};
		deleteObj.boardNum = num;
		$.ajax({
			url:'delete',
			data:deleteObj,
			type:'post',
			dataType:'json',
			success:function(res){ //위의 내용이 res로 들어가는거임 res는 변수명 같은것
				if(res){
					alert("삭제성공");
					location.href="list"
				}else {
					alert("답글있어 삭제 불가");
				}return
			},
			error:function(xhr,status,error){
				alert(error);
			}
		});
	}
};
function update(num) {
  	$('[name=boardnum]').val(num);
  	updateForm.submit();
}
function comment(num) {
  	$('[name=boardnum]').val(num);
  	commentForm.submit();
}
</script>
</head>
<body>
<!--네비시작-->
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header" onclick="name('Brand')">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
      </button>
      <a class="navbar-brand" href="list">게시판</a>
    </div>
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li onclick="name('Link')"><a href="#">Link <span class="sr-only">(current)</span></a></li>
        <li onclick="name('Link2')"><a href="#">Link2</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
         <sec:authorize access="!isAuthenticated()">
        	<li><a href="<c:url value='/login'/>">로그인</a></li>
        </sec:authorize>
        <sec:authorize access="isAuthenticated()">
	    	<li><a href="<c:url value='/login?logout' />">
					<c:if var="status" test="${secName ne 'anonymousUser'}">
					${secName} 님 로그아웃
					</c:if>
	    	</a></li>
		</sec:authorize>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<!--네비끝-->
	<form id="updateForm" action="updateform">
		<input type="hidden" name="boardnum">
		<input type="hidden" name="updateid" value=${detail.boardid}>
	</form>
	<form id="commentForm" action="commentform">
		<input type="hidden" name="boardnum">
	</form>
		<div id="div1">
			<div class="panel panel-default">
			<div class="panel-heading"><h3>상세 정보</h3></div>
			  	<div class="panel-body">
					<div name="title" id="btitle">
						제목 : ${detail.boardtitle}
					</div>
				</div>
			    <div class="panel-body">
					<div name="id" id="bauthor">
						작성자 : ${detail.boardid}
					</div>
				</div>
				<div class="panel-body">
					<div id="bemail">
						이메일 : <a href="/boardTest/user/emailform?emailid=${detail.email}">${detail.email}</a>
					</div>
			      </div>
			      <div class="panel-body">
			      	<div name="content" id="bcontent">
						내용 : ${detail.boardcontent}
					</div>
			      </div>
			      <div class="panel-body">
			      	<div name="content" id="bcontent">
						<c:choose>
							<c:when test="${secName eq 'anonymousUser'}">
								파일 이름 :${filedetail.filename}<br>
								파일 크기 : ${filedetail.filesize}
							</c:when>
							<c:otherwise>
								파일 이름 : <a href="download?upNum=${filedetail.upNum}">${filedetail.filename}</a><br>
								파일 크기 : ${filedetail.filesize}
							</c:otherwise>
						</c:choose>
					</div>
			      </div>
			</div>
		</div>
			<p>
			<p>
				<button type="button" class="btn btn-default" onclick="location.href='list'">리스트</button>
				&nbsp;&nbsp;
				<sec:authentication property="name" var="secName"/>
				<c:if test="${detail.boardid eq secName}">
					<button type="button" class="btn btn-default" onclick="javascript:update(${detail.boardNum})">수정</button>
					&nbsp;&nbsp;
					<button type="button" class="btn btn-default" onclick="javascript:del(${detail.boardNum});">삭제</button>
				</c:if>
				<sec:authorize access="hasAuthority('USER')">
				&nbsp;&nbsp;
				<button type="button" class="btn btn-default" onclick="javascript:comment(${detail.boardNum});">답글</button>
				</sec:authorize>
</body>
</html>