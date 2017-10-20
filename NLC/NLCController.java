package com.wunhill.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;

@Controller
public class NLCController {

	@ResponseBody
	@RequestMapping(value="/nlclassify",method=RequestMethod.POST)
	public String nlc(@RequestParam(value="text") String text,Model model){
		System.out.println("Text: "+text);
		
		//Creating instance of NLC
		NaturalLanguageClassifier service = new NaturalLanguageClassifier();
		
		//Authenticating with the service by providing the credentials
		service.setUsernameAndPassword("a1eded23-2949-462c-9740-becbf8eebf7f", "ffY8q5QJZciO");
		
		//Classifying the text by passing the classifier id and the text
		Classification classification = service.classify("589756x212-nlc-4344",
		  text).execute();
		
		System.out.println(classification);
		
		//Extracting the class
		String c1 = "It belongs to "+classification.getClasses().get(0).getName()+" Class.";
		System.out.println("Class Name: \n\n"+c1);
		
		return c1;
	}
}
