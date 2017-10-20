<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="speechToText" class="speechToText">
	<div class="col-md-6">
		<h2>Audio transcription</h2>
		<div>
			<form action="<c:url value="/speechtotext"/>"
				enctype="multipart/form-data" method="POST">

				<!-- Creating browse option for selecting a file -->
				<input type="file" name="audiofile" />

				<!-- Creating submit button -->
				<input type="submit" value="submit">

			</form>
		</div>
		<br>
	</div>

	<!-- div for showing the response -->
	<div class="col-md-5" style="width: 50%;">
		<h3>Transcript</h3>
		<div
			style="background: #ddd; border: 1px solid #ccc; padding: 10px; max-height: 200px; overflow-y: auto; border-radius: 4px;"
			id="nlc_resp">${result}</div>
	</div>
</div>