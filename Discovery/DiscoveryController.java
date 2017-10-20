package com.wunhill.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ibm.watson.developer_cloud.discovery.v1.Discovery;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryRequest;
import com.ibm.watson.developer_cloud.discovery.v1.model.query.QueryResponse;


@Controller
public class DiscoveryController {
	
	
	@RequestMapping(value = "/SearchLogistics", method = RequestMethod.GET, produces = "application/String; charset=utf-8")
	public  String showResponse(HttpServletRequest reques, HttpServletResponse response,
			@RequestParam("enterVal") String userQuery, Model mod) {
		
		String userName = " ";
		String password = " ";
		String collectionId = " ";
		String environmentId = " ";
		
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
		
	    //Checking the size of response and retrieving the result from json
		if(queryResponse.getResults().size()>0){
			
			String[] jsonvalue = new String[queryResponse.getResults().size()];
			String[] title = new String[queryResponse.getResults().size()];
			
			for(int i=0;i<(queryResponse.getResults().size());i++){
			 
				String text = ""+queryResponse.getResults().get(i).get("text");
				
				jsonvalue[i] = text.substring(9);
			}
			
			mod.addAttribute("result", jsonvalue);
			mod.addAttribute("size", jsonvalue.length);
		}
		else{
			mod.addAttribute("result", "No record found!!!");
			mod.addAttribute("title", "");
		}
		mod.addAttribute("resultfor", userQuery);
		return "discovery";
	}

}

