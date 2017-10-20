<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

				<h1 class="main-text">Language Translator</h1><br>
				<div class="row">
				<div class="col-md-8">
				&nbsp;&nbsp;<b>Choose which document you would like to view</b>
				
				<!-- Creating the select and calling the selfunction() -->
				<select id="langtr" onchange="selfunction()">
					<option id="opt" value="Not Selected">Please Select an option</option>
					<option id="dressCode" value="dress" >Dress Code</option>
					<option id="hrPolicy" value="hr">HR Policy</option>
					<option id="leavePolicy" value="leave">Leave Policy</option>
				</select>
				</div>
				</div><br><br>
				
				<!-- Creating the textarea -->
				<div class="col-md-5" style="width:40%;">
						<textarea id="text" name="text" rows="3" cols="40" placeholder="Choose a document to view or enter your own text" style="background:white; border:1px solid #ccc; padding:10px; max-height:200px; overflow-y:auto; border-radius:4px;"></textarea>&nbsp;&nbsp;
				</div>
				<div class="col-md-2" style="width:20%;">
				<br><br>
				<!-- <Creating select for the language -->
				<select id="ltype"style="height:30px;">
							<option value="Not Selected">Select Target Language</option>
							<option id="en_es" value="en-es-conversational">Spanish</option>
								<option value="en-de">German</option>
							<option value="en-pt">Portuguese</option>
							<option value="en-fr-conversational">French</option>
						</select>
						<br>
				<button type="button" class="trans-text"  id="ltranslate" name="ltranslate" onclick="callTranslate()" style="height:38px; width:177px;">Translate here</button>
				</div>
			
				<div class="col-md-5"style="width:40%;">
					<label class="label-text">Response:</label>
					<textarea id="convert_resp" name="text" rows="3" cols="40" style="background:white; border:1px solid #ccc; padding:10px; max-height:200px; overflow-y:auto; border-radius:4px;"></textarea>&nbsp;&nbsp;
					
				</div>
				
<script type="text/javascript">

//Function to be called on submit button for language translator
function callTranslate(){
	var sel_value = document.getElementById("langtr").value;
	var language = document.getElementById("ltype").value;
	var v = document.getElementById("text").value;
	if(sel_value=="Not Selected"){
		alert("Please select an option.")
		return false;
	}
	else if(v == "" || v==null){
		alert("Please enter some text.");
		return false;
	}
	else if(language=="Not Selected"){
		alert("Please select a target language.");
		return false;
	}
	else{
	//Making ajax call to translate the text
		$.ajax({
			url : 'LanguageTranslator',
			type : 'POST',
			data : {
				'text' : $("#text").val(),
				'ltype' : $("#ltype").val()
			},
			success : function(data) {
				console.log("data: "+ JSON.stringify(data));
				document.getElementById("convert_resp").innerHTML = data;
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Error Occurred Please Try lgain later !!");
			}
		});
	}
}


//Function for filling the textarea with sample text for select
function selfunction(){
 	var sel_value = document.getElementById("langtr").value;
	if(sel_value=="dress"){
		var input = "We want our drivers to be relaxed and comfortable. Although Wunhill does not have a uniform, we do require you to follow our dress code."+
			"We recommend you buy good quality work boots and gloves. Work boots should have non-skid, oil-resistant soles. Double-layer gloves are recommended for winter."+
			"Ensure that you keep a bag of extra clothing for extreme weather.";
		document.getElementById("text").innerHTML = input;
		
	} 
	else if(sel_value=="hr"){
		var input = "Human resources management policies are formalized, documented and approved by the Board or approval as designated to the CEO/ED. Human resources management policies comply with employment, workplace health and safety, and other related legislation as is applicable in the jurisdiction in which the organization operates.";
		document.getElementById("text").innerHTML = input;
		
	}
	else if(sel_value=="leave"){
		var input = "Wunhill drivers earn 1 day of paid time off for every 10 driving days."+
			"Wunhill drivers earn 4 paid sick days per year."+
			"Managers must be notified of scheduled leave one week ahead of time."+
			"Wunhill drivers may be asked to work on holidays if routes and schedules demand it. Drivers receive double time for holiday driving days.";
		document.getElementById("text").innerHTML = input;
		
	}
	else{
		alert("Please select a option.");
		return false;	
	}
}
		
</script>
