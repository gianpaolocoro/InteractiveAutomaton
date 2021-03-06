package org.unirender.interaction.automaton;

import it.gp.sockets.remote.orvibo.commons.OrviboSocket;
import it.gp.sockets.remote.orvibo.communication.OrviboCommunication;

import org.unirender.asr.wholeword.language.RecognizerPreset;

public class SimpleAutomaton {
	/*
	public static void main(String[] args) throws Exception{
		String regex="assistente|UNO";
		String phrase = "assistente";
		System.out.println(Pattern.matches(regex, phrase));
	}
	*/
	public static void main(String[] args) throws Exception{
		
		
		EmbeddedAutomaton automListener = new EmbeddedAutomaton(true, RecognizerPreset.WORDS);
		EmbeddedAutomaton automActivator = new EmbeddedAutomaton(false, RecognizerPreset.YESNO);
		
		String hotword = automListener.getHotWord();
		String activationhotword = automListener.getActivationDeactivation();
		
		System.out.println("Hotword "+hotword);
		System.out.println("ActivationHotword "+activationhotword);
		
		double thresholdHotword = automListener.getThresholdHotword();
		double thresholdWord = automListener.getThresholdActivationWord();
		
		int nBest = automListener.getNBest();
		
		System.out.println("Thresholds: "+thresholdHotword+" ; "+thresholdWord);
		
		//String address = "192.168.1.35"; //orto
		String address = automListener.getPlugAddress(); //studio
//		String address = "192.168.1.183"; //salotto
		
		OrviboSocket socket	= new OrviboSocket(address, 0);
		
		while (true){
			
			automListener.recognizeHotword(hotword, thresholdHotword, nBest);
			
			if (socket.getStatus()== 0){
				automListener.say("Periferica spenta");
				automListener.say("Vuoi accenderla?");
			}
			else{
				automListener.say("Periferica accesa"); 
				automListener.say("Vuoi spegnerla?");
			}
			boolean recognized = automActivator.recognizeOneShot(activationhotword, thresholdWord, 1);
			
			if (recognized){
				if (socket.getStatus()== 0)
					automListener.say("Accensione");
				else
					automListener.say("Spegnimento");
				
				
				OrviboCommunication communication = new OrviboCommunication(socket.getAddress());
				communication.switchStatus();
				socket.setStatus(communication.getStatus());
				System.out.println("status set to "+communication.getStatus());
				
				automListener.say("Torno in attesa.");
			}
			else{
				automListener.say("Operazione annullata. Torno in attesa.");
			}
		}
		//autom.shutdown();
	}


	

	
}
