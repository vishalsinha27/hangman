<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="../js/jquery-1.11.1.min.js"></script>

<title>Hackman Game</title>

<script type="text/javascript">

  
function playgame(id)
{
	var type ;
	var url ;
	if(id==null) {
		url =  '../ws/games' ;
		type = 'post' ;
	}else {
		url =  '../ws/games/'+id ;
		type = 'get' ;
	}
	
	$.ajax({
	    url: url,
	    type: type,
	    dataType: "json",
	    
	    success: function(result) {
	    	createGame(result);
	    }
	});

	function createGame(data) {
		var div = $("<div />") ;
	    $("#myDiv").append(div); 

	    for(var i=0;i<data.correctGuess.length;i++) {
	    	if(data.correctGuess[i] == '.') {
	    		data.correctGuess[i] = '' ;
	    	}
	    	div.append($("<input id='"+i+"' value='"+data.correctGuess[i]+"' ></input>")) ;
	    	document.getElementById("retry").innerHTML= data.numberOfRetries ;
	    	$('#'+i).attr('readonly', 'readonly');
	    }
	    
	    $( "input" ).bind('keyup', function(e) {
	    	document.getElementById("text").innerHTML = 'You pressed '+$(this).val() ;
	    	$.ajax({
	    	    url: "../ws/games/"+id+"/"+$(this).val(),
	    	    type: "post",
	    	    dataType: "json",
	    	    
	    	    success: function(result) {
	    			//alert(JSON.stringify(result)) ;
	    			if(result.code && result.code==106) {
	    				document.getElementById("gameover").style.display= 'block' ;
	    				document.getElementById("guess").disabled= 'true' ;

	    				
	    			} else if(result.code && result.code==110) {
	    				document.getElementById("gamedone").style.display= 'block' ;
	    				document.getElementById("guess").disabled= 'true' ;

	    			}
	    			
	    			else if(result.entryType=='error') {
	    				document.getElementById("incorrect").style.display= 'block' ;
	    				document.getElementById("correct").style.display= 'none' ;
	    				document.getElementById("retry").innerHTML= result.numberOfRetries ;

					
	    			}else {
	    				//document.getElementById("correct").style.display= 'block' ;
	    				//document.getElementById("incorrect").style.display= 'none' ;
	    				// document.getElementById("retry").innerHTML= result.numberOfRetries ;
	    			    document.location.reload();

	    				
	    			}
	    			
	    	    }
	    	});	    	
	    
	    
	    }) ;

	}

	
	
	
	$( "button" ).bind('onclick', function(){
		
		window.location.href = "index.jsp" ;
	}) ;
	
	
	
}

function changeLocation(loc) {
	window.location.href = loc ;
}


</script>

</head>
<body onload="playgame(<%=request.getParameter("id") %>)">
	<h1>Playing Game id  <%=request.getParameter("id") %></h1>
	<div>
		Enter the character in the box.
	</div>
	<br>
	 <div id="myDiv"></div>
	 
	 <br>
	 Enter your guess: <input id="guess" type="text">
	<br>
	<div id="text"> </div>
	<br>
	<div id="gamedone" style="display:none;">Game is successfully completed. No Entries allowed </div>
	<br>
	<div id="gameover" style="display:none; ;color:red;">Number of Retries already exhausted </div>
	<br>
	<div id="correct" style="display:none; ;color:green;">Bingo!! Correct Guess </div>
	<br>
	<div id="incorrect" style="display: none;color: red;">Sorry Wrong Guess </div>
	<br>
	<div>
	   Number Of Retries : <span id="retry"></span>
	</div>
	<br>
	<br>
	<button name="Back" onclick="changeLocation('../')">Go Back</button>
</body>

</html>