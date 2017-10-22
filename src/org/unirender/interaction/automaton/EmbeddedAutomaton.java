package org.unirender.interaction.automaton;

import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import org.unirender.asr.wholeword.audio.VAD;
import org.unirender.asr.wholeword.language.RecognizerPreset;
import org.unirender.asr.wholeword.language.SupportedLanguages;
import org.unirender.asr.wholeword.recognizer.LiveSpeechRecognizer;
import org.unirender.tts.mary.Mary;
import org.unirender.tts.mary.MaryServer;

public class EmbeddedAutomaton {
	MaryServer TTSserver;
	Mary tts;
	LiveSpeechRecognizer asr;
	Properties properties;
	boolean TTSactive;

	public EmbeddedAutomaton(boolean TTSactive,
			RecognizerPreset recognizerPreset) throws Exception {
		init(TTSactive, recognizerPreset);
	}

	public void say(String phrase) throws Exception {
		if(TTSactive)
			tts.say(phrase);
		else
			System.out.println("'°< : "+phrase);
	}

	public void init(boolean TTSactive, RecognizerPreset recognizerPreset)
			throws Exception {
		FileInputStream fis = new FileInputStream(
				new File("process.properties"));
		properties = new Properties();
		properties.load(fis);
		asr = new LiveSpeechRecognizer(SupportedLanguages.IT, getModelsPath(),
				recognizerPreset);
		setVADParameters();
		this.TTSactive = TTSactive;
		if (TTSactive) {
			TTSserver = new MaryServer(getMaryPath());
			TTSserver.startServer();
			tts = new Mary(getMaryVoice());
			tts.setBlueToothAudio(getUseBlueToothAudio());
			tts.say("sistema attivato.");
		}
	}

	public void shutdown() throws Exception {
		if (TTSactive) {
			tts.say("sistema in spegnimento.");
			TTSserver.shutdownServer();
		}
	}

	public String getPlugAddress() {
		return properties.getProperty("plugIPAddress");
	}

	public String getHotWord() {
		return properties.getProperty("hotword");
	}

	public String getActivationDeactivation() {
		return properties.getProperty("activationHotword");
	}

	public String getMaryPath() {
		return properties.getProperty("maryPath");
	}

	public String getMaryVoice() {
		return properties.getProperty("maryVoice");
	}

	public double getThresholdHotword() {
		return Double.parseDouble(properties.getProperty("thresholdHotword"));
	}

	public double getThresholdActivationWord() {
		return Double.parseDouble(properties.getProperty("thresholdActivationWord"));
	}
	
	public float getSensitivity() {
		return Float.parseFloat(properties.getProperty("sensitivity"));
	}

	public float getMaxSilence() {
		return Float.parseFloat(properties.getProperty("maxSilence"));
	}

	public float getMaxSilenceAfterWord() {
		return Float.parseFloat(properties.getProperty("maxSilenceAfterWord"));
	}

	public float getMaxSpeech() {
		return Float.parseFloat(properties.getProperty("maxSpeech"));
	}

	public int getNBest() {
		return Integer.parseInt(properties.getProperty("nBest"));
	}

	
	public File getModelsPath() {
		return new File(properties.getProperty("modelsPath"));
	}

	public boolean getUseBlueToothAudio() {
		return Boolean
				.parseBoolean(properties.getProperty("useBlueToothAudio"));
	}

	private void setVADParameters(){
		VAD vad = asr.getVoiceActivityDetector();
		vad.setMaxInitialSilence(getMaxSilence());
		vad.setMaxSilenceAfterWord(getMaxSilenceAfterWord());
		vad.setSensitivity(getSensitivity());
		vad.setMaxSpeechTime(getMaxSpeech());
	}
	
	public void recognizeHotword(String hotword, double threshold, int maxNBest)
			throws Exception {

		boolean caught = false;
		while (!caught) {
			String word = asr.listenAndRecognize();
			caught = analyseResult(word, hotword, threshold, maxNBest);
		}
	}

	public boolean analyseResult(String word, String hotword, double threshold, int maxNBest){
		boolean caught = false;
		
		if (word == null) {
			// tts.say("o");
		} else {
			LinkedHashMap<String, Double> nbest = asr.getNBest();
			int counter = 1;
			for (String recognized : nbest.keySet()) {
				double score = nbest.get(recognized);
				System.out.println(counter + ": " + recognized+" "+score);
				if (Pattern.matches(hotword, recognized) && (score>threshold) ){
					System.out.println("Hotword!\n");
					caught = true;	
					break;
				}
				counter ++;
				if (counter>maxNBest)
					break;
			}
		}
		
		if (!caught)
			System.out.println("No match\n");
		
		return caught;
	}
	
	public boolean recognizeOneShot(String hotword, double threshold, int maxNBest)
			throws Exception {

		String word = asr.listenAndRecognize();

		return analyseResult(word, hotword, threshold, maxNBest);
		
	}

}