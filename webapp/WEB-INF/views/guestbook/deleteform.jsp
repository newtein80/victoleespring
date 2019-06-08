<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록 삭제</title>
</head>
<body>
	<form action="/guestbook/main/delete?no=${ param.no }" method="post">
		<table>
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pwd"></td>
				<td><input type="submit" value="확인"></td>
			</tr>
		</table>
	</form>
	<a href="/guestbook/main/index">메인으로 돌아가기</a>
</body>
</html>