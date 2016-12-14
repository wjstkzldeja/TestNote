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
body{text-align:center;}
#div1{margin:0px auto;width:440px;}
#div2{
	border: 1px solid gray; 
	border-spacing:0px;
	margin:10px auto;width: 90%;
	padding:10px;
}
#title{text-align: left;}
#sendperson{text-align: left;}
#content{text-align: left;}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
function save() {
	var title = $('[name=title]').val();
	//var email = $('#emailid').val();
	var email = "${emailid}";
	var content = $('[name=content]').val();
	var emailObj ={};
	alert(email);
	emailObj.emailAddr = email;
	emailObj.emailtitle = title;
	emailObj.emailcontent =content;

	$.ajax({
		url:'emailsend',
		data:emailObj,
		type:'POST',
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
function cancle() {
	location.href="/boardTest/board/list"
}

</script>
</head>
<body>
	<p>
	<div id="div1">
		<div class="panel panel-default">
		  <div class="panel-heading"><h3>이메일 쓰기</h3></div>
		  <div class="panel-body">
			<div id="title">
				제목<input type="text" name="title" maxlength="30" size="45" value="메일제목">
			</div>
		  </div>
		  <div class="panel-body">
			<div id="sendperson">
				받는사람 : ${emailid}
			</div>
		  </div>
		  <div class="panel-body">
			<div id="content">
				내용<textarea name="content" rows="20" cols="47" maxlength="400" style="resize: none">메일내용</textarea>
			</div>
		  </div>
		</div>
	</div>
	<p>
	<p>
		<button type="button" class="btn btn-default" onclick="javascript:cancle()">취소</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-default" onclick="javascript:save()">확인</button>
</body>
</html>