<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid" id="texttospechpage">
	<div class="container-fluid">
		<div class="row">
			<div class="col-md-6">
			<!-- Creating label -->
				<h3>Input text to be analyzed</h3>
				
				<!-- Creating textbox -->
				<input type="text" class="form-control" id="textVal"
					style="width: 80%; border-radius: 0px;"
					placeholder="text to be analyzed"><br> 
					<!-- Creating button and onclick calling the callAnalyze() method -->
					<input
					type="button" class="btn btn-info" value="Analyze Text" id="button"
					onclick="callAnalyze()" style="border-radius: 0px;">
			</div>
			<div class="col-md-6">
				<h3>Response</h3>
				<pre id="AnalyzeData5"></pre>
			</div>
		</div>
	</div>
</div>

<script>
	//Function to call the nluchanged controller by ajax
	function callAnalyze() {
		$.ajax({
			url : 'nluchanged',
			type : 'POST',
			data : {
				'textVal' : $('#textVal').val()
			},
			success : function(data) {
				$("#AnalyzeData5").text(data);

			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Error Occurred Please Try lgain later !!");
			}
		});
	}
</script>