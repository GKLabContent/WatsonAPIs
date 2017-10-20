<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Image Tagging Tool</h1>
<div class="col-md-6">
	<form method="POST" action="ImageRecognisation"
		enctype="multipart/form-data">

		<!-- Label for displaying the name of the selected image -->
		<label class="control-label" for="file">Image upload:</label>

		<!-- Browse option for selecting the file and callig the readURL() method-->
		<input type="file" name="file" onchange="readURl(this);" id="imgInp">
		<br /> <br /> <br />
		<p style="color: red" id="error"></p>

		<!-- submit button -->
		<input class="btn btn-success" type="submit" value="Upload"
			id="btnuploadimg">

		<!-- Image to be shown after browsing -->
		<img id="blah" src="#" alt="your image" style="max-width: 271px;" />

	</form>
</div>

<!-- div for response -->
<div class="col-md-6">
	<h3>Response:</h3>
	<pre>
	<c:if test="${responseSize > 0}">
		Total images processed: <c:out value="${responseSize}" />
			<br>
		<table width="90%">		
		<!-- table heading -->
			<tr>
					<th>Image name</th>
					<th>Score</th>
					<th>Image Class</th>
				</tr>
				
		<!-- Looping through the response and assigning them into the table body -->
		<c:forEach var="i" begin="0" end="${responseSize-1}">
			<c:set var="data" value="${images[i]}" />
			<c:set var="scoredata" value="${scores[i]}" />
			<c:set var="cdata" value="${c_type[i]}" />
			<tr>
						<td><c:out value="${data }" /></td>
						<td><c:out value="${scoredata }" /></td>
						<td><c:out value="${cdata }" /></td>
					</tr>
		</c:forEach>
		</table>
	</c:if>
	</pre>
</div>

<script type="text/javascript">
	// Jquery function to show the selected image
	$(document).ready(function() {
		$('#blah').attr('src', localStorage.getItem("tempsrc"));
		localStorage.setItem("tempsrc", "");
	});

	//Jquery function to read the url of the image
	function readURl(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#blah').attr('src', e.target.result);
				localStorage.setItem("tempsrc", e.target.result);
			}
			reader.readAsDataURL(input.files[0]);
		}
	}
</script>
