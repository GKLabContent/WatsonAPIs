package com.wunhill.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.rtj.model.ToneAnaPOJO;
import com.rtj.model.Tones;

@Controller
public class ToneAnalyzerController {

	@RequestMapping(value = "/ToneAnalyzer", method = RequestMethod.GET, produces = "application/String; charset=utf-8")
	public String toneAnalyzer(Model model,
			@RequestParam("textTone") String textTone) {
		System.out.println("textTone "+textTone);
		Gson gson = new Gson();
		
		//Creating instance of ToneAnalyzer by passing the version date as constructor
		ToneAnalyzer service2 = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
		
		//Authenticating with the service
		service2.setUsernameAndPassword("49514d92-4b74-41fc-bbaf-84c967f1f8d1", "xZTrNgy7tOWa");

		//Getting the tone
		ToneAnalysis tone = service2.getTone(textTone, null).execute();
		System.out.println("toneee" + tone);
		
		List<Tones> emotional = new ArrayList<Tones>();
		List<Tones> language = new ArrayList<Tones>();
		List<Tones> social = new ArrayList<Tones>();
		ToneAnaPOJO tonepojo = gson.fromJson(tone.toString(), ToneAnaPOJO.class);
		
		
		//Extracting emotional, language and social tones
		// textFromPojo=tonepojo.getDocument_tone().getTone_categories()[0].getCategory_name();
		Tones[] emotionalTones = tonepojo.getDocument_tone().getTone_categories()[0].getTones();
		Tones[] languageTones = tonepojo.getDocument_tone().getTone_categories()[1].getTones();
		Tones[] socialTones = tonepojo.getDocument_tone().getTone_categories()[2].getTones();
		Map<String, String> finalArray = new HashMap<String, String>();

		emotional = Arrays.asList(emotionalTones);
		language = Arrays.asList(languageTones);
		social = Arrays.asList(socialTones);

		//Sorting the emotional list
		Collections.sort(emotional, new Comparator<Tones>() {

			@Override
			public int compare(Tones o1, Tones o2) {
				// TODO Auto-generated method stub

				if (Float.parseFloat(o1.getScore()) < Float.parseFloat(o2.getScore()))
					return 1;
				if (Float.parseFloat(o1.getScore()) > Float.parseFloat(o2.getScore()))
					return -1;
				return 0;
			}
		});
	
		//Sorting the language list
		Collections.sort(language, new Comparator<Tones>() {

			@Override
			public int compare(Tones o1, Tones o2) {
				// TODO Auto-generated method stub

				if (Float.parseFloat(o1.getScore()) < Float.parseFloat(o2.getScore()))
					return 1;
				if (Float.parseFloat(o1.getScore()) > Float.parseFloat(o2.getScore()))
					return -1;
				return 0;
			}
		});
		
		//Sorting the social list
		Collections.sort(social, new Comparator<Tones>() {

			@Override
			public int compare(Tones o1, Tones o2) {
				// TODO Auto-generated method stub

				if (Float.parseFloat(o1.getScore()) < Float.parseFloat(o2.getScore()))
					return 1;
				if (Float.parseFloat(o1.getScore()) > Float.parseFloat(o2.getScore()))
					return -1;
				return 0;
			}
		});

		//Setting the Model
		model.addAttribute("emotional",  emotional.get(0).getTone_name().toString());
		model.addAttribute("social", social.get(0).getTone_name().toString());
		model.addAttribute("language", language.get(0).getTone_name().toString());
		return "toneanalyzer";
	}


	
}
