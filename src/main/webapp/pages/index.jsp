<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src="js/jquery-1.11.1.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>HangMan Game</title>

<script type="text/javascript">
	function changeLocation(loc) {
		window.location.href = loc ;
	}
	
	function createGame() {
		$.ajax({
		    url: 'ws/games',
		    type: "post",
		    dataType: "json",
		    
		    success: function(result) {
		    	changeLocation('pages/playgame.jsp?id='+result.gameId) ;
		    }
		});
	}
</script>
</head>
<body>

	<h1>Welcome to Hangman</h1>
	<br>
	<input type="button" name="Play" value="Play New Game" onclick="createGame()"/>
	<input type="button" name="Stats" value="Get Stats of Games" onclick="changeLocation('pages/gamedetails.jsp')"/>
	
</body>
</html>