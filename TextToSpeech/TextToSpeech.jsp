<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="container-fluid" id="texttospechpage">
	<div class="container-fluid">
		<h3>Text to Speech</h3>

		<!-- text box to enter the text -->
		<input type="text" class="form-control" id="textVal"
			style="width: 40%; border-radius: 0px;"
			placeholder="text to be converted">

		<!-- Submit button -->
		<input type="button" class="btn btn-info" value="Submit" id="button"
			style="border-radius: 0px;">

		<!-- div for holding the response -->
		<div id="audioDiv"></div>
	</div>
</div>

<script>
	//Jquery function to call the PlayAudio() method
	$("#button").click(
			function() {

				//Reading the entered text value
				var textVal = encodeURIComponent(document
						.getElementById("textVal").value);

				//Assigning the audio for the text
				var audio = new Audio('PlayAudio?textVal=' + textVal);
				audio.controls = true;

				//Appending the audio into the browser
				document.body.appendChild(audio);

			});
</script>