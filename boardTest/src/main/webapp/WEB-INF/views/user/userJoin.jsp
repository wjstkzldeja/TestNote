<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>회원 가입</title>
<!--네비시작-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--네비끝-->
 <style type="text/css">
#div1{width: 600px;margin: 0px auto;}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
 $(function() {
   	$('[name=email]').attr('disabled',false);
	$('[name=id]').attr('disabled',true);
	$('[name=pwd]').attr('disabled',true);
	$('[name=join]').attr('disabled',true);
	$('[name=joinbutton]').attr('disabled',true);
	$('[name=idcheckbt]').attr('disabled',true);
 	if('${email}'!=""){
		$('[name=emailtag]').attr('disabled',true);
		$('[name=id]').attr('disabled',false);
		$('[name=pwd]').attr('disabled',false);
		$('[name=join]').attr('disabled',false);
		$('[name=joinbutton]').attr('disabled',false);
		$('[name=idcheckbt]').attr('disabled',false);
	}
});
function emailCheck() {
	var email = $('[name=emailtag]').val();
	var emailObj ={};
	emailObj.emailAddr = email;
	alert(email);

	$.ajax({
		url:'emailCheck',
		data:emailObj,
		type:'post',
		dataType:'json',
		success:function(res){
			if(res.result){
				alert('본인 메일에서 인증을 끝내주세요');
				return;
			}else alert('메일 보내기 실패');
		},
		error:function(xhr,status,error){
			alert(error);
		}
	});
}
var checkafterid;
function idCheck() {
	var id= $('[name=id]').val();
	var pwd= $('[name=pwd]').val();
	var regType1 = /^[A-Za-z0-9+]{3,15}$/;
 	if(id == ""){
		alert('ID 입력해 주세요');
		return
	}else if (!regType1.test(id)){
		alert('영문 또는 숫자 3~10 자리 입력해 주세요');
		return
	}
	var idObj ={};
	idObj.id = id;

	$.ajax({
		url:'idcheck',
		data:idObj,
		type:'post',
		dataType:'json',
		success:function(res){
			if(res){
				alert('사용 가능한 아이디');
				checkafterid = id;
			}else {
				alert('중복된 아이디');
				return
			}
		},
		error:function(xhr,status,error){
			alert(error);
		}
	});
}
function join() {

	var id= $('[name=id]').val();
	var pwd= $('[name=pwd]').val();
	var email= $('[name=email]').val();
	alert(email);
	if(id == "" || pwd == ""){
		alert('ID 또는 PWD 입력해 주세요');
	}else if(!(checkafterid == id)){
	alert('중복 검사 해주세요')
	}else joinForm.submit();
}
</script>
</head>
<body>
<p>
<div class="panel panel-default" id="div1">
<div class="panel-heading"><h3 style="text-align: center;">회원가입</h3></div>
	<p>
	<form class="form-horizontal" id="joinForm" action="join">
	<input type="hidden" name="email" value="${emailid}">
	  <div class="form-group">
	    <label for="inputEmail3" class="col-sm-2 control-label">Email</label>
	    <div class="col-sm-7">
	      <input type="text" name="emailtag" class="form-control" id="inputEmail3" placeholder="Email 입력" value="${emailid}">
	    </div>
	    <button id="checkButton" type="button" onclick="javascript:emailCheck();" class="btn btn-default">유효성 검사</button>
	  </div>
	  <div class="form-group">
	    <label for="exampleInputName2" class="col-sm-2 control-label">I D</label>
	    <div class="col-sm-7">
	      <input type="text" name="id" class="form-control" id="exampleInputName2" placeholder="ID 입력">
	    </div>
	    <button type="button" name="idcheckbt"onclick="javascript:idCheck();" class="btn btn-default">아이디 중복 체크</button>
	  </div>
	  <div class="form-group">
	    <label for="inputPassword3" class="col-sm-2 control-label">PWD</label>
	    <div class="col-sm-7">
	      <input type="password" name="pwd" class="form-control" id="inputPassword3" placeholder="PWD 입력 ">
	    </div>
	  </div>
	  <div class="form-group">
	    <div class="col-sm-offset-2 col-sm-10">
	      <button name="joinbutton" type="button" onclick="join();" class="btn btn-default">회원 가입</button>
	      <button type="button" onclick="location.href='/boardTest/login'" class="btn btn-default">로그인 하러</button>
	    </div>
	  </div>
	</form>
</div>

</body>
</html>