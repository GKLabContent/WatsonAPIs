<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

				<h1>Types Of Service</h1>
				<div class="col-md-5" style="width:40%;">
						<label>Enter your text</label><br>
						<textarea id="text" name="text" cols="40" style="background:#ddd; border:1px solid #ccc; padding:10px; max-height:200px; overflow-y:auto; border-radius:4px;"></textarea>&nbsp;&nbsp;
				</div>
				<div class="col-md-2" style="width:20%;">
				<br><br>
				<button type="button" id="classify" name="classify" onclick="callclassify()" style="height:30px; width:100px;">Classify</button>
				</div>
			
				<div class="col-md-5"style="width:40%;">
					<h3>Response:</h3>
					<div style="background:#ddd; border:1px solid #ccc; padding:10px; max-height:200px; overflow-y:auto; border-radius:4px;" id="nlc_resp"></div>
					
				</div>
				
<script type="text/javascript">

//Function to perform the validation and making an ajax call
function callclassify(){
	var text = document.getElementById("text").value;
	if((text=="")||(text==null)){
		alert("Please enter the text.");
		return false;
	}
	else{
		//Making ajax call
		$.ajax({
			url : 'nlclassify',
			type : 'POST',
			data : {
				'text' : $("#text").val(),
			},
			success : function(data) {
				console.log("data: "+ JSON.stringify(data));
				document.getElementById("nlc_resp").innerHTML = data;
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Error Occurred Please Try lgain later !!");
			}
		});
	}
}
		
</script>
