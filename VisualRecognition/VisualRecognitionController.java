package com.wunhill.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;


@Controller
public class VisualRecognitionController {

	@RequestMapping(value = "/ImageRecognisation", method = RequestMethod.POST)
	public String ImageRecognisation(@RequestParam("file") MultipartFile file,
			Model model) throws IOException {
		System.out.println(file);
		File convFile = convert(file);

		// Creating the instance of VR service
		VisualRecognition service = new VisualRecognition(
				VisualRecognition.VERSION_DATE_2016_05_20);

		// Authenticating to the service by providing the Service API key
		service.setApiKey("apikey");

		System.out.println("Classify an image");

		// Classifying the images in your classifier
		ClassifyImagesOptions options = new ClassifyImagesOptions.Builder()
				.classifierIds("classifierIds")
				.images(convFile).build();

		// Executing the classify option
		VisualClassification result = service.classify(options).execute();
		System.out.println(result);
		Gson gson = new Gson();
		model.addAttribute("lpcurpage", "logistic");

		model.addAttribute("vr_resp", gson.toJson(result));
		model.addAttribute("responseSize", result.getImagesProcessed());
		 
		String[] images = new String[result.getImagesProcessed()];
		int[] score = new int[result.getImagesProcessed()];
		String[] class_type = new String[result.getImagesProcessed()];
		
		for (int i = 0; i < result.getImagesProcessed(); i++) {
			//Getting total no of images processed
			images[i] = result.getImages().get(i).getImage();
			
			//Getting score of each images
			score[i] = (int) (result.getImages().get(i).getClassifiers().get(0)
					.getClasses().get(0).getScore() * 100);
			
			//Getting class of each images
			class_type[i] = result.getImages().get(i).getClassifiers().get(0)
					.getClasses().get(0).getName();
		}
		
		//Setting the model
		model.addAttribute("images", images);
		model.addAttribute("scores", score);
		model.addAttribute("c_type", class_type);
		return "visualrecognition";
	}

	// Converting MultipartFile to File
	public File convert(MultipartFile file) throws IOException {
		File convFile = new File(file.getOriginalFilename());
		convFile.createNewFile();
		FileOutputStream fos = new FileOutputStream(convFile);
		fos.write(file.getBytes());
		fos.close();
		return convFile;
	}

}
