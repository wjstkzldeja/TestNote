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
#div1{margin:0px auto;width:440px;}

#btitle{text-align: left;}
#bcontent{text-align: left;}
</style>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script type="text/javascript">
	 function save(num) {
	if(confirm('저장 하시겠습니까?')){
		var title = $('[name=titleInfo]').val();
		var content = $('[name=contentInfo]').val();
		var id = '${secName}';
		if (title == "" || content == "") {
			alert("제목 또는 내용을 입력해 주세요");
			return;
		}else{
			var commentObj = {};
			commentObj.boardtitle = title;
			commentObj.boardcontent = content;
			commentObj.boardid = id;
			commentObj.boardpwd = "pwd";
			commentObj.commentNum = num;
		$.ajax({
			url : 'comment',
			data : commentObj,
			type : 'post',
			dataType : 'json',
			success : function(res) { //위의 내용이 res로 들어가는거임 res는 변수명 같은것
				if(res.result){
				alert("저장 완료");
				location.href = "detail?boardNum="+res.boardnum+"&boardid="+id;
				}else alert("저장 실패");
			},
			error : function(xhr, status, error) {
				alert(error);
			}
		});
		}
	}
	};
	function cancle(id) {
		alert('취소');
		location.href = "list";
	}
</script>
</head>
<body>
	<p>	
		<div id="div1">
		<div class="panel panel-default">
		  <div class="panel-heading"><h3>답 글쓰기</h3></div>
		  <div class="panel-body">
			<div name="title" id="btitle">
				제목 <input type="text" name="titleInfo" maxlength="20" size="45">
			</div>
		  </div>
		  <div class="panel-body">
		    	<div name="content" id="bcontent">
				내용<textarea name="contentInfo" rows="20" cols="47" maxlength="400" style="resize: none"></textarea>
				</div>
		  </div>
		</div>
	</div>
	<p>
	<p>
		<button type="button" class="btn btn-default" onclick="javascript:cancle()">취소</button>
		&nbsp;&nbsp;
		<button type="button" class="btn btn-default" onclick="javascript:save(${boardnum})">확인</button>

</body>
</html>