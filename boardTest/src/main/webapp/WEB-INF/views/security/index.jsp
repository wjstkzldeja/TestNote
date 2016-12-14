<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"  uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>메뉴</title>
</head>
<body>
<h3>가능한 메뉴</h3>
 
<ul>
<sec:authorize access="hasAuthority('USER_MANAGER')">
    <li><a href="<c:url value='/admin/usermanager/main' />">사용자 관리자</a></li>
</sec:authorize>
 
<sec:authorize access="hasAuthority('USER')">
<li><a href="<c:url value='/board/list' />">게시판 들어가기</a></li>
</sec:authorize>
 
<sec:authorize access="!isAuthenticated()">
    <li><a href="<c:url value='/login' />">로그인</a></li>
    <li><a href="<c:url value='/user/email'/>">회원가입</a></li>
</sec:authorize>
 
<sec:authorize access="isAuthenticated()">
    <li><a href="<c:url value='/login?logout' />">로그아웃</a></li>
</sec:authorize>
</ul>
 
</body>
</html>