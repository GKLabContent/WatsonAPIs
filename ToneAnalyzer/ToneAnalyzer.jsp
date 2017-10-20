<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<br />
<div class="text-right">

	<!-- Adding label -->
	<label class="col-sm-3 col-sm-offset-2 srch-label">Enter text
		for tone analyzer</label>

	<div class="col-sm-7">
		<form action="<c:url value="/ToneAnalyzer"/>">

			<div class="input-group">

				<!-- Adding text box -->
				<input type="text" class="form-control input-sm "
					placeholder="Search" id="textTone" name="textTone" align="left">

				<div class="input-group-btn ">

					<!-- Adding submit button -->
					<input type="submit" class="btn btn-default btn-sm"
						onclick="toneResponse()" id="discoveryTab" /> <i
						class="glyphicon glyphicon-search"></i>

				</div>
			</div>
		</form>
	</div>

</div>

<!-- div for response -->
<div class="col-md-6" id="toneResult"
	style="margin-top: 10%; margin-left: 36%;">
	<p>
		<b>Social Tone: </b> ${social}
	</p>

	<p>
		<b>Emotional Tone: </b> ${emotional}
	</p>
	<p>
		<b>Language Tone: </b> ${language}
	</p>



</div>


