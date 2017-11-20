<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
.morecontent span {
	display: none;
}

.morelink {
	display: block;
}
</style>
<div id="discoverySearch" class="discoverySearch">

	<div class="text-right">

		<!-- Creating label before the text box -->
		<label class="col-sm-3  srch-label">Search for hazardous material guidelines</label>
		<div class="col-sm-5">
			<form action="<c:url value="/SearchLogistics"/>">
				<div class="input-group">

					<!-- Creating textbox and button for taking input and submitting the form -->
					<input type="text" class="form-control input-sm "
						placeholder="Search" id="enterVal" name="enterVal" align="left">
					<div class="input-group-btn ">
						<input class="btn btn-default btn-sm" type="submit"
							onclick="responseDisplay()" id="discoveryTab" /> <i
							class="glyphicon glyphicon-search"></i>

					</div>
				</div>
			</form>
		</div>


	</div>
	<div id="SearchResult" style="padding: 90px">

		<!-- Itterating through all the response, so that we can call the jquery function on each response -->
		<c:if test="${size > 0}">
			<c:forEach var="i" begin="0" end="${size -1}">
				<br>
				<div class="more">
					<c:out value="${result[i]}" />
					<br>
				</div>
			</c:forEach>
		</c:if>
	</div>
</div>

<script>
	//Jquery function for showing the more and less text
	$(document)
			.ready(
					function() {
						// Configure/customize these variables.
						var showChar = 100; // How many characters are shown by default
						var ellipsestext = "...";
						var moretext = "Show More <br><br>";
						var lesstext = "Show Less<br><br>";

						$('.more')
								.each(
										function() {
											var content = $(this).html();

											if (content.length > showChar) {

												var c = content.substr(0,
														showChar);
												var h = content.substr(
														showChar,
														content.length
																- showChar);

												var html = c
														+ '<span class="moreellipses">'
														+ ellipsestext
														+ '&nbsp;</span><span class="morecontent"><span>'
														+ h
														+ '</span>&nbsp;&nbsp;<a href="" class="morelink">'
														+ moretext
														+ '</a></span>';

												$(this).html(html);
											}

										});

						$(".morelink").click(function() {
							if ($(this).hasClass("less")) {
								$(this).removeClass("less");
								$(this).html(moretext);
							} else {
								$(this).addClass("less");
								$(this).html(lesstext);
							}
							$(this).parent().prev().toggle();
							$(this).prev().toggle();
							return false;
						});
					});
</script>