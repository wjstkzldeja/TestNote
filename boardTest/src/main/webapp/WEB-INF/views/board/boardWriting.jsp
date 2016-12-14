<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>

<sec:authentication property="name" var="secName"/>
<!DOCTYPE html >
<html>
<head>
<meta charset="utf-8">
<title>게시판</title>
<script src="https://code.jquery.com/jquery-2.2.4.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>
<!--네비시작-->
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!--네비끝-->
<script type="text/javascript" src="/boardTest/resources/smarteditor/js/HuskyEZCreator.js" charset="utf-8"></script>

<style type="text/css">
body {
	text-align: center;
	margin: 0px auto;
}
#div1{margin:0px auto;width:440px;}
#btitle{text-align: left;}
#bauthor{text-align: left;}
#bcontent{text-align: left;}
#up{text-align: left; padding-top:5px;margin-left: 42px;}
</style>
<script type="text/javascript">

$(function(){
	//전역변수선언
    var editor_object = [];
    nhn.husky.EZCreator.createInIFrame({
        oAppRef: editor_object,
        elPlaceHolder: "boardcontent",
        sSkinURI: "/boardTest/resources/smarteditor/SmartEditor2Skin.html", 
        htParams : {
            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,             
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : true,     
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : false, 
        }
    }); 
	/*  */
	 savebt.onclick = function() {
							alert("들어옴");
							 editor_object.getById["boardcontent"].exec("UPDATE_CONTENTS_FIELD", []);
						} 
			 var options = {
					
			
			complete: function(response) 
			    {	
			    	var jsObj=eval('('+response.responseText+')');
			        $("#message").html("<font color='green'>"+response.responseText+"</font>");
			       if(jsObj.result){
			    	   alert('저장성공');
			           location.href = "detail?boardNum="+jsObj.boardnum+"&boardid=${secName}";
			       }else{
			    	   alert('저장실패');
			    	   return;
			       }
			   },
			    error: function()
			    {
			        $("#message").html("<font color='red'> ERROR: unable to upload files</font>");
			    }
			}; // end of options
			 
			     $("#uploadForm").ajaxForm(options);
			
			});
 function cancle(num) {
 	location.href = "list";
 }
</script>
</head>
<body>
	<p>
	<form id="uploadForm" name="uploadForm" action="writing" method="post" enctype="multipart/form-data">
	<input type="hidden" name="boardid" value="${secName}">
	<input type="hidden" name="boardpwd" value="pwd">
	<div id="div1">
		<div class="panel panel-default">
		  <div class="panel-heading"><h3>글쓰기</h3></div>
		  <div class="panel-body">
			<div id="btitle">
				제목 <input type="text" name="boardtitle" maxlength="30" size="45">
			</div>
		  </div>
		  <div class="panel-body">
		  	<div id="bauthor">
				작성자 : ${secName}
			</div>
		  </div>
		  <div class="panel-body">
		    <div id="bcontent">
				내용<textarea name="boardcontent" id="boardcontent" rows="20" cols="47" maxlength="400" style="resize: none"></textarea>
			</div>
		  </div>
		  	<!--업로드 폼-->
			<div id="up">
					<input type="file" name="file"><br>
			</div>
		</div>
	</div>
	<p>
	<p>
		<button type="button" class="btn btn-default" onclick="javascript:cancle()">취소</button>
		&nbsp;&nbsp;
		<button id="savebt" type="submit" class="btn btn-default">확인</button>
	</form>
	
	<p>
</body>
</html>