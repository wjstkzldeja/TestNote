<%@page contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>이메일 검사 폼</title>
 <style type="text/css">
body{text-align:center;}
table{border:1px solid black;border-spacing:0px;margin:0px auto;padding:10px}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
$(function() {
	$('[name=email]').attr('disabled',false);
	$('[name=id]').attr('disabled',true);
	$('[name=pwd]').attr('disabled',true);
	$('[name=join]').attr('disabled',true);
 	if('${email}'!=""){
		$('[name=email]').attr('disabled',true);
		$('[name=id]').attr('disabled',false);
		$('[name=pwd]').attr('disabled',false);
		$('[name=join]').attr('disabled',false);
	}
});
function idCheck() {
	var email = $('[name=email]').val();
	//location.href="emailTest="+email"";
	var emailObj ={};
	emailObj.emailAddr = email;

	$.ajax({
		url:'emailTest',
		data:emailObj,
		type:'post',
		dataType:'json',
		success:function(res){
			if(res.result){
				alert('메일 보내기 성공');
			}else alert('메일 보내기 실패');
		},
		error:function(xhr,status,error){
			alert(error);
		}
	});
}
function join() {
	joinForm.submit();
	
}
</script>
</head>
<body>
email : ${email}
emailAddr : ${emailAddr }
<p>
<h3>이메일 검사 폼</h3>
<form id="joinForm" action="join">
<table>
	<tr>
		<th>EMAIL</th>
		<td><input type="email" name="email" value="wjstkzldeja@naver.com"></input></td>
		<td><button id="checkButton" type="button" onclick="javascript:idCheck();">유효성 검사</button></td>
	</tr>
	<tr>
		<th>ID</th>
		<td><input type="text" name="id" id="id"></input></td></tr>
	<tr>
		<th>PASS</th>
		<td><input type="password" name="pwd" class="cls1"></input></td>
	</tr>
	<tr>
		<td><button type="button" onclick="join();">회원 가입</button></td>
	</tr>
</table>
</form>
</body>
</html>