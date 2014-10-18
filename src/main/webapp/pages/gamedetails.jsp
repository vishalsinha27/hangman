<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
table {
  border: 1px solid #666;   
    width: 100%;
}
th {
  background: #f8f8f8; 
  font-weight: bold;    
    padding: 2px;
}
td {
	text-align: center;
	}
</style>
<script src="../js/jquery-1.11.1.min.js"></script>

<title>Game Details</title>
<script type="text/javascript">


function getGameData()
{
	
	$.ajax({
	    url: '../ws/games',
	    type: "get",
	    dataType: "json",
	    
	    success: function(result) {
			//alert(JSON.stringify(result)) ;
	    	drawTable(result);
	    }
	});

	function drawTable(data) {
	    for (var i = 0; i < data.length; i++) {
	        drawRow(data[i]);
	    }
	}

	function drawRow(rowData) {
	    var row = $("<tr />")
	    $("#myDiv").append(row); 
	    row.append($("<td><a href='playgame.jsp?id="+rowData.gameId+"''>" + rowData.gameId + "</a></td>"));
	    row.append($("<td>" + rowData.state + "</td>"));
	    row.append($("<td>" + rowData.numberOfRetries + "</td>"));
	    row.append($("<td>" + rowData.correctGuess.length + "</td>"));

	}
}
</script>

</head>
<body onload="getGameData()">
	<h1>Game Details </h1>
	<table id="myDiv">
    <tr>
        <th>Game Id</th>
        <th>Current State</th>
        <th>Number of Retries left</th>
        <th>Length of Word</th>
        
    </tr>
    
</table></body>
</html>