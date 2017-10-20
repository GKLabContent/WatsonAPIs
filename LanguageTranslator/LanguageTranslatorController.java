package com.wunhill.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.watson.developer_cloud.language_translator.v2.LanguageTranslator;

@Controller
public class LanguageTranslatorController {
	
	@ResponseBody
	@RequestMapping(value="/LanguageTranslator",method=RequestMethod.POST)
	public String translate(@RequestParam(value="text") String text,@RequestParam(value="ltype") String to, Model model){
		
		//Creating instance of LanguageTranslaor
		LanguageTranslator service = new LanguageTranslator();
		
		//Authenticating with username and password
		service.setUsernameAndPassword("73899959-a45f-4e31-8512-d054e2388d0a","ttqsdExPFvQW");

		//Making request for translation by passing the text and the target language
		com.ibm.watson.developer_cloud.language_translator.v2.model.TranslationResult result1 =  service.translate(text, to).execute();
		
		//Extracting the translated text from response 
		String result = result1.getTranslations().get(0).getTranslation();
		
		//Adding the result into model to fetch in the front-end
		model.addAttribute("vr_resp", result);
		System.out.println(result);
		
		return result+"";	
	}		
}
