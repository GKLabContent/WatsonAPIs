package com.wunhill.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;

@Controller
public class SpeechToTextController {
	
	@RequestMapping(value = "/speechtotext", method = RequestMethod.POST)
	public String spechtotext(@RequestParam("audiofile") MultipartFile file, Model model) throws RuntimeException, IOException, JSONException {
		System.out.println("Inside Speech t text-----" + file);
		
		//Creating instance of SpeechToText
		SpeechToText service = new SpeechToText();
		
		//Authenticating with service
		service.setUsernameAndPassword("3219e64f-6da3-4364-8721-e02946e2efa7", "DvPMcqwP7G4C");

		//Configuring the RecognizeOptions by setting contentType
		RecognizeOptions options = new RecognizeOptions.Builder()
		  .contentType("audio/mp3").timestamps(true).interimResults(true)
		  .wordAlternativesThreshold(0.9)
		  .keywords(new String[]{"where", "truck", "my", "know"})
		  .keywordsThreshold(0.5).build();

		System.out.println("Going to work on file...");
		
		//Recognizing the speech
		SpeechResults results = service.recognize(convert(file), options).execute();
		System.out.println(results);
			
		System.out.println(results.getResultIndex());
		
		//Extracting text from jsonResponse and setting the model
		model.addAttribute("result", results.getResults().get(0).getAlternatives().get(0).getTranscript()); 
		
		return "speechtotext";

	}

	//Converting MultipartFile to File
	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}
	
}
