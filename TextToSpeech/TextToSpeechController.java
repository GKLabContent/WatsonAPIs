package com.wunhill.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.AudioFormat;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

@Controller
public class TextToSpeechController {

	@RequestMapping(value = "/PlayAudio", method = RequestMethod.GET)
	public void play(ModelAndView model, @RequestParam(value = "textVal") String text, HttpServletResponse res)
			throws IOException {
		System.out.println("inside spring controller" + text);
		
		//Creating instance of TextToSpeech
		TextToSpeech tts = new TextToSpeech();

		//Authenticating with username and password
		tts.setUsernameAndPassword("Username", "Password");
		InputStream ins;
		OutputStream out = null;
		
		//Getting the voice
		Voice voice = tts.getVoice("en-US_AllisonVoice").execute();

		//Synthesizing the voice in .wav format
		ins = tts.synthesize(text, voice, AudioFormat.WAV).execute();
		
		//Setting the MIME type of the response
		res.setContentType("AddType audio/ogg .ogg");
		out = res.getOutputStream();
		byte[] buffer = new byte[2048];
		int read;
		while ((read = ins.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

	
}
