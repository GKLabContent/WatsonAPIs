package com.wunhill.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

@Controller
public class ConversationController {
	static MessageResponse response = null;
	
	static Map context = new HashMap();
	
	@RequestMapping(value = "/chat", method = RequestMethod.POST)
	public static @ResponseBody String chat(@RequestParam(value="inp") String input) {
		
		System.out.println(input);
		System.out.println("Chat Only");
		
		//Calling conversationAPI
		response = conversationAPI(input,context);
		
		context = response.getContext();
		
		//Returning response
		return response.getText().get(0);
	}
	
	public static MessageResponse conversationAPI(String input,Map context){
		  
		//Creating the instance of Watson Conversation API by passing the Version of Conversation Service 
		ConversationService service = new ConversationService("2017-06-09");
		
		//Authenticating with the Watson Conversation API by providing the Username and Password
		service.setUsernameAndPassword("80a79cfa-b27d-4e56-8dc7-0221db74308c", "tUy7FRpwBQlX");
		
		//Creating message query
		MessageRequest newMessage = new MessageRequest.Builder()
		.inputText(input).context(context).build();
		String workspaceId = "882de57b-b701-4ce7-b00b-fd5429e607f2";
		
		//Calling the message() method with the workspaceId and query 
		MessageResponse response = service.message(workspaceId, newMessage).execute();

		return response;
		} 
	
	

}
