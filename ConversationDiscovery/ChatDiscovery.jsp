<style>
.col-md-2, .col-md-10 {
	padding: 0;
}

.panel {
	margin-bottom: 0px;
}

.chat-window>div>.panel {
	border-radius: 5px 5px 0 0;
}

.msg_container_base {
	background: #e5e5e5;
	margin: 0;
	padding: 0 10px 10px;
	max-height: 200px;
	overflow-x: hidden;
}

.top-bar {
	background: #666;
	color: white;
	padding: 10px;
	position: relative;
	overflow: hidden;
}

.msg_receive {
	padding-left: 0;
	margin-left: 0;
}

.msg_sent {
	padding-bottom: 20px !important;
	margin-right: 0;
}

.messages {
	background: white;
	padding: 10px;
	border-radius: 2px;
	box-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
	max-width: 100%;
}

.messages>p {
	font-size: 13px;
	margin: 0 0 0.2rem 0;
}

.messages>time {
	font-size: 11px;
	color: #ccc;
}

.msg_container {
	padding: 10px;
	overflow: hidden;
	display: flex;
}

.avatar img {
	display: block;
	width: 100%;
}

.avatar {
	position: relative;
}

.messages p {
	color: #222 !important;
}

.base_receive>.avatar:after {
	content: "";
	position: absolute;
	top: 0;
	right: 0;
	width: 0;
	height: 0;
	border: 5px solid #FFF;
	border-left-color: rgba(0, 0, 0, 0);
	border-bottom-color: rgba(0, 0, 0, 0);
}

.base_sent {
	justify-content: flex-end;
	align-items: flex-end;
}

.base_sent>.avatar:after {
	content: "";
	position: absolute;
	bottom: 0;
	left: 0;
	width: 0;
	height: 0;
	border: 5px solid white;
	border-right-color: transparent;
	border-top-color: transparent;
	box-shadow: 1px 1px 2px rgba(black, 0.2);
}

.msg_sent>time {
	float: right;
}

.msg_container_base::-webkit-scrollbar-track {
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
	background-color: #F5F5F5;
}

.msg_container_base::-webkit-scrollbar {
	width: 12px;
	background-color: #F5F5F5;
}

.msg_container_base::-webkit-scrollbar-thumb {
	-webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, .3);
	background-color: #555;
}

.btn-group.dropup {
	position: fixed;
	left: 0px;
	bottom: 0;
}

#btn-input {
	padding: 8px;
	height: 40px;
	font-size: 15px;
}

#btn-input:focus {
	outline: 0;
	background: #fff;
	border: 1px solid rgba(0, 188, 212, 0.67);
	-moz-box-shadow: none;
	-webkit-box-shadow: none;
	box-shadow: none;
}

.inp-box {
	margin: -8px;
	margin-top: 5px;
	padding: 4px;
	border-top: 2px solid #ddd;
}

.main-chat-window {
	padding: 20px;
}

.morecontent span {
	display: none;
}

.morelink {
	display: block;
}
</style>
</head>
<body>



	<div class="main-chat-window " id="ChatBoxS">
		<div class="row chat-window col-xs-5 col-md-5" id="chat_window_1"
			style="margin-left: 10px;">
			<div class="col-xs-12 col-md-12">
				<div class="panel panel-default">
					<div class="panel-heading top-bar">

						<!-- Creating chat logo and giving headings  -->
						<div class="col-md-8 col-xs-8">
							<h3 class="panel-title">
								<span class="glyphicon glyphicon-comment"></span> We are Online
							</h3>
						</div>

					</div>

					<!-- Creating container for displaying the request and response -->
					<div class="panel-body msg_container_base">
						<div id='chatDiv'></div>
					</div>


					<!-- Creating a text box for taking input and making an ajax call -->
					<div class="panel-footer">
						<input id="btn-input" type="text"
							class="form-control input-sm chat_input mytext"
							placeholder="Write your message here..." />

					</div>

				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">

var $chat = $('.msg_container_base');
var bottom = true;

//Function to scroll the chatbox
$chat.bind('scroll', function () {
  var $scrollTop = $(this).scrollTop();
  var $innerHeight = $(this).innerHeight();
  var $scrollHeight = this.scrollHeight;
  bottom = $scrollTop + $innerHeight >= $scrollHeight ? true : false;
});

var me = {};
me.avatar = "https://www.shareicon.net/data/128x128/2016/08/18/813775_man_512x512.png";

var you = {};
you.avatar = "https://www.shareicon.net/data/128x128/2016/09/15/829460_user_512x512.png";

//Function to format the date
function formatAMPM(date) {
    var hours = date.getHours();
    var minutes = date.getMinutes();
    var ampm = hours >= 12 ? 'PM' : 'AM';
    hours = hours % 12;
    hours = hours ? hours : 12; // the hour '0' should be '12'
    minutes = minutes < 10 ? '0'+minutes : minutes;
    var strTime = hours + ':' + minutes + ' ' + ampm;
    return strTime;
}            

//-- No use time. It is a javaScript effect.
function insertChat(who, text, time = 0){
    var control = "";
    var date = formatAMPM(new Date());
    
    if (who == "me"){
    	//Adding the text, avatar and the date
    	control ='<div class="row msg_container base_receive ">'
                            	   +'<div class="col-md-2 col-xs-2 avatar">'
                            	   +'<img src="'+ me.avatar +'">'
                            	   +' </div>'
                            	   +'<div class="col-md-10 col-xs-10">'
                            	   +'<div class="messages msg_receive ">'
                            	   +' <p>'+text+'</p>'
                            	   +'<time datetime="2009-11-13T20:00">'+date+'</time>'
                            	   +'</div>'
                            	   +' </div>'
                            	   +' </div>';
    
		//Calling the ConversationDiscoveryController
        $.post("./chatdiscover",
           	    {
        	inp: text
           	    },
           	    function(data, status){
insertChat("you", data, 0);
                
           	    }); 
                    
                    
                    
    
    }else{
    	//Adding the text, avatar and the date
                  control ='<div class="row msg_container base_sent">'
           					 	+'<div class="col-md-10 col-xs-10">'
                				+'<div class="messages msg_sent more">'
		                    	+'<p>'+text+'</p>'
		                    	+'<time datetime="2009-11-13T20:00">'+date+'</time>'
		                    	+'</div>'
		                   		+'</div>'
		                   		+'<div class="col-md-2 col-xs-2 avatar">'
		                   		+'<img src="'+ you.avatar +'">'
		                   		+'</div>'
		                   		+'</div>';           
    }
    setTimeout(
        function(){                        
            
        	$("#chatDiv").append(control);
        	 if (bottom) {
        		    // Only animate to bottom on 'chat message' if bottom = true
        		    $chat.animate({scrollTop: $chat.prop("scrollHeight")}, 500);
        		  }

        }, time);
    
}


//Clearing the chatbox
function resetChat(){
    $("#chatDiv").empty();
}

/* Ajax call for response on press of Enter key*/
$(".mytext").on("keyup", function(e){
	//Checking wether the pressed key in Enter or not.
    if (e.which == 13){
        var text = $(this).val();
        if (text !== ""){
            insertChat("me", text);              
            $(this).val('');
        }
    }
});

//-- Clear Chat
resetChat();

 </script>

</body>