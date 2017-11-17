package com.wunhill.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryResponse;

/**
 * @author Ranjit Jadhav
 *
 */

@Controller
public class ConversationDiscoveryController {
	static MessageResponse response = null;

	static Map context = new HashMap();

	@RequestMapping(value = "/chatdiscover", method = RequestMethod.POST)
	public static @ResponseBody String chatdiscover(@RequestParam(value = "inp") String input) throws JSONException {
		System.out.println(input);

		System.out.println("Chat Plus Discovery");
		
		//Calling conversationAPI
		response = conversationAPI(input, context);
		context = response.getContext();

		System.out.println(response.getOutput().get("nodes_visited"));
		String Str = response.getOutput().get("nodes_visited").toString();
		System.out.println("Str " + Str);
		
		//Comparing response of conversationAPI(), If it is [Anything else] then calling Discoveryapicall()
		if (Str.equals("[Anything else]")) {
			System.out.println("Go to Discovery");

			ArrayList<String> wordslist = new ArrayList<String>();
			wordslist.add("Corrosive");
			wordslist.add("Radioactive");
			wordslist.add("Explosives");
			wordslist.add("Gases");
			wordslist.add("Gasses");
			wordslist.add("Flammable");
			wordslist.add("Combustible");
			wordslist.add("Liquids");
			wordslist.add("Oxidizer");
			wordslist.add("Organic");
			wordslist.add("Peroxide");
			wordslist.add("Poison");
			wordslist.add("Handle");
			wordslist.add("Ship");
			wordslist.add("Transfer");
			boolean sst = false;
			for (String string : wordslist) {
				if (input.contains(string)) {
					sst = true;
				}
			}
			if (sst) {
				//Calling DiscoveryAPI
				String res = Discoveryapicall(input);
				//Returning response
				return res;
			}
		}

		System.out.println(response.getText().get(0));
		return response.getText().get(0);
	}

	public static MessageResponse conversationAPI(String input, Map context) {
		
		//Creating the instance of Watson Conversation API by passing the Version of Conversation Service
		ConversationService service = new ConversationService("2017-06-09");
		
		//Authenticating with the Watson Conversation API by providing the Username and Password
		service.setUsernameAndPassword("username", "password");
		
		//Creating message query
		MessageRequest newMessage = new MessageRequest.Builder().inputText(input).context(context).build();
		String workspaceId = "workspaceId";
		
		//Calling the message() method with the workspaceId and query
		MessageResponse response = service.message(workspaceId, newMessage).execute();

		return response;
	}

	public static String Discoveryapicall(String userQuery) throws JSONException {

		String userName = "username";
		String password = "password";
		String collectionId = "collectionId";
		String environmentId = "environmentId";
		
		//Creating the instance of Watson Discovery API by passing the Version of Discovery Service
		Discovery discovery = new Discovery("2016-12-01");
		
		//Setting the end point
		discovery.setEndPoint("https://gateway.watsonplatform.net/discovery/api");
		
		//Setting the username and password
		discovery.setUsernameAndPassword(userName, password);
		
		//Building querybuilder for delivering the content from the collection
		QueryRequest.Builder queryBuilder = new QueryRequest.Builder(environmentId, collectionId);		
		queryBuilder.query(userQuery);
		
		//Executing the query and assigning the response in the queryResponse
		QueryResponse queryResponse = discovery.query(queryBuilder.build()).execute();
		
		int size = 1;
		if (queryResponse.getResults().size() > 0) {
			size = queryResponse.getResults().size();
		}
		String fresp[] = new String[size];

		if (queryResponse.getResults().size() > 0) {
			for (int j = 0; j < size; j++)
				fresp[j] = "" + queryResponse.getResults().get(j).get("value");
		} else {
			fresp[0] = "I didn't understand. You can try rephrasing.";
		}

		return fresp[0];
	}

}
