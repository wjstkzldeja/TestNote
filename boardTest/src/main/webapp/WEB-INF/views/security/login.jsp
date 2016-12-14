<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>로그인</title>
<!--네비시작-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--네비끝-->
 <style type="text/css">
#div1{width: 500px;margin: 0px auto;}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript"></script>
</head>
<body>

<p>
<div class="panel panel-default" id="div1">
<div class="panel-heading"><h3 style="text-align: center;">로그인</h3></div>
<p>
	<c:if test="${not empty param.error}">
	<!--파라메터에 에러가 있으면 밑에 span 을 띄워라-->
	    <span id="errMsg">오류: ${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
	    <!--SPRING_SECURITY_LAST_EXCEPTION 에러 이름임  -->
	</c:if>
	<form class="form-horizontal" id="joinForm" action="<c:url value='user/login'/>" method="post">
	  <div class="form-group">
	    <label for="exampleInputName2" class="col-sm-2 control-label">I D</label>
	    <div class="col-sm-7">
	      <input type="text" name="id" class="form-control" id="exampleInputName2" placeholder="Id" value="wjstkzldeja">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="inputPassword3" class="col-sm-2 control-label">PWD</label>
	    <div class="col-sm-7">
	      <input type="password" name="pwd" class="form-control" id="inputPassword3" placeholder="Pwd" value="1234">
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button type="submit" class="btn btn-default">로그인</button>
	      <button type="button" onclick="location.href='board/list'" class="btn btn-default">손님 입장</button>
	      <button type="button" onclick="location.href='user/email'" class="btn btn-default">회원가입</button>
	    </div>
	  </div>
	</form>
<p>
</div>
<%session.invalidate();%>
</body>
</html>