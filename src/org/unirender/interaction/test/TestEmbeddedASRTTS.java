package org.unirender.interaction.test;

import java.io.File;

import org.unirender.asr.wholeword.language.RecognizerPreset;
import org.unirender.asr.wholeword.language.SupportedLanguages;
import org.unirender.asr.wholeword.recognizer.LiveSpeechRecognizer;
import org.unirender.tts.mary.Mary;
import org.unirender.tts.mary.MaryServer;

public class TestEmbeddedASRTTS {
	
	
	public static void main(String[] args) throws Exception {
		MaryServer TTSserver = new MaryServer("../MaryTTS/marytts-5.2/");
		TTSserver.startServer();
		Mary tts = new Mary("istc-lucia-hsmm");
		LiveSpeechRecognizer asr = new LiveSpeechRecognizer(
				SupportedLanguages.IT,new File("/home/pi/workspace/WholeWordSpeechRecognizer/MODELS/"),RecognizerPreset.WORDS);
		double threshold = -10000;
		while (true) {

			tts.say("pronuncia la parola UNO");
			String word = asr.listenAndRecognize();
			if (word == null)
				tts.say("non ho sentito niente");
			else {
				//tts.say("hai detto " + word);
				if (word.equalsIgnoreCase("UNO") && (asr.getScore()>threshold)) {
					tts.say("la parola è giusta");
					break;
				}
				else{
					tts.say("Parola errata");
				}
			}
		}
		
		TTSserver.shutdownServer();

	}

}