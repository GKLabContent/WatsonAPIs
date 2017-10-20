package com.wunhill.services;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ibm.watson.developer_cloud.natural_language_understanding.v1.NaturalLanguageUnderstanding;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalysisResults;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.AnalyzeOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.EmotionOptions;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.Features;
import com.ibm.watson.developer_cloud.natural_language_understanding.v1.model.SentimentOptions;

@Controller
public class NLUChangedController {
	
	//Creating NLU instance and Authenticating with NLU by passing the version,username and password for the service
	static NaturalLanguageUnderstanding service = new NaturalLanguageUnderstanding(
			NaturalLanguageUnderstanding.VERSION_DATE_2017_02_27, "Username",
			"Password");
	
	@ResponseBody
	@RequestMapping(value="/nluchanged", method=RequestMethod.POST)
	public String analyze(@RequestParam(value="textVal") String textVal,Model model){
		
		//Finding the index of "same-day deliver service or same-day delivery" from the input passed by user
		int index = textVal.lastIndexOf("same-day deliver service");
		int index1 = textVal.lastIndexOf("same-day delivery");
		String result = null;
		String result1=null;
		
		//If the user input contains the same-day delivery or same-day deliver service 
		if((index>-1) || (index1>-1)){
			
			//Getting the sentiment of the text
			SentimentOptions sentiment = new SentimentOptions.Builder().build();
			Features features = new Features.Builder().sentiment(sentiment).build();
			AnalyzeOptions parameters = new AnalyzeOptions.Builder().text(textVal).features(features).build();
			AnalysisResults sentimentresponse = service.analyze(parameters).execute();
			
			//Extracting the sentiment score from the sentimetnresponse
			int score = (int)((sentimentresponse.getSentiment().getDocument().getScore())*100);
			result = "Sentiment \nScore: "+score;
			
			//Getting the emotion from the text 
			EmotionOptions emotion1 = new EmotionOptions.Builder().document(true).build();

			Features features1 = new Features.Builder().emotion(emotion1).build();

			AnalyzeOptions parameters1 = new AnalyzeOptions.Builder().text(textVal).features(features1).build();

			AnalysisResults response = service.analyze(parameters1).execute();
			
			//Extracting the anger, disgust, fear, joy and sadness from the text
			int anger = (int)(response.getEmotion().getDocument().getEmotion().getAnger()*100);
			int disgust = (int)(response.getEmotion().getDocument().getEmotion().getDisgust()*100);
			int fear = (int)(response.getEmotion().getDocument().getEmotion().getFear()*100);
			int joy = (int)(response.getEmotion().getDocument().getEmotion().getJoy()*100);
			int sadness = (int)(response.getEmotion().getDocument().getEmotion().getSadness()*100);
			
	 result1 = "Emotion: \n Anger: "+anger+"\nDisgust: "+disgust+"\nFear: "+fear+"\nJoy: "+joy+"\nSadness: "+sadness;
		}
		//If user input does not contain the word same-day delivery or same-day deliver service
		else{
			return "This text may not relate to same-day delivery.";
		}
		
		return ""+result+"\n\n"+result1;
	}

}
