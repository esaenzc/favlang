<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
</head>
<body>
	<div>
		<h2>FAVLANG say's:</h2>
		<h1>Internal server error</h1>

		<p>Excepcion message: ${exception}</p>
		<p>Requested url: ${url}</p>

		<form action="/favlang">
			<input type="submit" id="submit" value="Back">
		</form>

	</div>



</body>
</html>
