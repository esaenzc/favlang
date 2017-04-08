<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<style>
<!-- This code should go in a css file -->
table {
	font-family: arial, sans-serif;
	border-collapse: collapse;
	width: 100%;
}

td, th {
	border: 1px solid #dddddd;
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

.blueText {
	color: blue;
}

.redText {
	color: red;
}
</style>

</head>
<body>

	<h1>GitHub User's Favorite Programming Languages</h1>
	<h2>Please introduce a Github username</h2>
	
	<p>You can try: kitodo, jbtadmin, timgrossmann, vireshmanagooli, testgithubpua</p>

	<form action="/favlang">
		Username: <input type="text" name="username" value="${username}"> <input
			type="submit" value="Submit">
	</form>
	<p class="redText">${error}</p>


	<c:if test="${not empty languages}">
		<table>
			<c:forEach var="entry" items="${languages}" varStatus="loop">
				<c:choose>
					<c:when test="${loop.index == 0}">

						<tr>
							<th>Programming Language</th>
							<th>Number of bytes coded</th>
						</tr>
						<tr>
							<td class="blueText">${entry.name} (Favorite Programming Language)</td>
							<td class="blueText">${entry.codeBytes}</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td>${entry.name}</td>
							<td>${entry.codeBytes}</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</table>
	</c:if>


</body>
</html>
