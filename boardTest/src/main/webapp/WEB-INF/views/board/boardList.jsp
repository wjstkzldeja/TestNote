<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<sec:authentication property="name" var="secName"/>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>게시판</title>

<!--네비시작-->
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--네비끝-->

<script src="//code.jquery.com/jquery-2.1.3.min.js"></script>
<script src="//netdna.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="//raw.github.com/botmonster/jquery-bootpag/master/lib/jquery.bootpag.min.js"></script>
<link rel="stylesheet"href="//netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
<script src="<c:url value="/resources/jquery.bootpag.min.js"/>"></script>

<script>
	$(function() {
		$('#page-selection').bootpag({
			total : ${total},
			page : ${pageNum},
			maxVisible : 5,
			leaps : true,
			firstLastUse : true,
			first : '←',
			last : '→',
			wrapClass : 'pagination',
			activeClass : 'active',
			disabledClass : 'disabled',
			nextClass : 'next',
			prevClass : 'prev',
			lastClass : 'last',
			firstClass : 'first'
		}).on("page", function(event, num) {
			location.href="list?pageNum="+num+"&dropBox=${dropBox}&searchText=${searchText}";
		});
	});
    function search() {
    	var searchText = $('[name=searchText]').val();
    	var searchMenu = $('[name=searchMenu]').val();
    	if(searchText==""){
    		alert('내용을 입력해 주세요');
    	}else{
    		location.href="list?searchText="+searchText+"&dropBox="+searchMenu;
    	}
	}
</script>
<style type="text/css">
body {
	text-align: center;
}
#namediv{text-align: left;}
#tablediv{width: 60%;margin: 0px auto;}
th,td {height:35px;}
th{border-bottom: 1px solid black; background-color:lightgray; text-align:center;}
td{border-bottom: 1px solid black;}
#tnum{width:60px;}
#ttitle{width:550px;}
#tid{width:100px;}
#tdate{width:100px;};
#btitle{text-align: left;}
#btitle:hover {background-color:skyblue;}
#a1{text-decoration: none; color:black;}
#a1:HOVER {color: red;}
#sd{padding:1px 0 5.5px 0;}
</style>
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
        <li onclick="name('Link')"><a href="#">메뉴1<span class="sr-only">(current)</span></a></li>
        <li onclick="name('Link2')"><a href="#">메뉴2</a></li>
      </ul>
      <ul class="nav navbar-nav navbar-right">
         <sec:authorize access="!isAuthenticated()">
        	<li><a href="<c:url value='/login'/>">로그인</a></li>
        	<li><a href="<c:url value='/user/email'/>">회원 가입</a></li>
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
	<p>
	<h3>게시판</h3>
<div id="tablediv">
		<c:choose>
			<c:when test="${secName eq 'anonymousUser'}">
				<div id="namediv">Guest 님 접속을 환영합니다!!</div>
			</c:when>
			<c:otherwise>
				<div id="namediv">${secName} 님 접속을 환영합니다!!</div>
			</c:otherwise>
		</c:choose>
	<form id="detailForm">
		<table class="table table-bordered">
			<tr>
				<th id="tnum">번호</th>
				<th id="ttitle">제목</th>
				<th id="tid">작성자</th>
				<th id="tdate">작성일</th>
			</tr>
	<%-- 		<c:forEach var="e" items="${list}">
				<tr id="trv">
					<td id="bnum">${e.boardNum}</td>
					<td id="btitle"><a id="a1" href="detail?boardNum=${e.boardNum}&boardid=${e.boardid}">${e.boardtitle}</a></td>
					<td id="bid">${e.boardid}</td>
					<td id="bdate">${e.boardhiredate}</td>
				</tr>
			</c:forEach> --%>
		 	<c:forEach var="e" items="${list}">			
				<c:choose>
					<c:when test="${e.active == 0}">
						<tr id="trv">
							<td id="bnum">${e.boardNum}</td>
							<td id="btitle1" style="text-align: left;"><a id="a1" href="detail?boardNum=${e.boardNum}&boardid=${e.boardid}">${e.boardtitle}</a></td>
							<td id="bid">${e.boardid}</td>
							<td id="bdate">${e.boardhiredate}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr id="trv">
							<td id="bnum">${e.boardNum}</td>
							<td id="btitle">삭제된 게시글 입니다</td>
							<td id="bid">${e.boardid}</td>
							<td id="bdate">${e.boardhiredate}</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</table>
	</form>
</div>
	<p>
	<div>
		<div id="content"></div>
		<div id="page-selection"></div>
	</div>
	<p>
	<div>
		<select name="searchMenu" id="sd">
			<option value="boardTitle" ${dropBox=='boardTitle'?'selected':''}>제목</option>
			<option value="boardNum" ${dropBox=='boardNum'?'selected':''}>게시물 번호</option>
			<option value="boardId" ${dropBox=='boardId'?'selected':''}>작성자</option>
		</select> 
		&nbsp;<input type="text" name="searchText" value="${searchText}">&nbsp;&nbsp;
		<button type="button" class="btn btn-default" onclick="javascript:search()">검색</button>&nbsp;&nbsp;
		<sec:authorize access="hasAuthority('USER')">
		<button type="button" class="btn btn-default" onclick="location.href='writingform'">글쓰기</button>&nbsp;&nbsp;
		</sec:authorize>
		<button type="button" class="btn btn-default" onclick="location.href='list'">전체보기</button>
	</div>

</body>
</html>