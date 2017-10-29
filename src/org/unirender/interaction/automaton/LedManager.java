package org.unirender.interaction.automaton;

public class LedManager {

	static String [] init = {"/bin/bash","-c","sudo su root -c \"echo none>/sys/class/leds/led0/trigger\""};
	static String [] off = {"/bin/bash","-c","sudo su root -c \"echo backlight >/sys/class/leds/led0/trigger\""};
	static String [] on = {"/bin/bash","-c","sudo su root -c \"echo default-on >/sys/class/leds/led0/trigger\""};
	static String [] heartbeat = {"/bin/bash","-c","sudo su root -c \"echo heartbeat>/sys/class/leds/led0/trigger\""};
	static String [] timer = {"/bin/bash","-c","sudo su root -c \"echo timer>/sys/class/leds/led0/trigger"};
	
	public boolean ledExists;
	
	public LedManager(){
		String[] command = {"uname","-a"};
		String OSName = OSInteraction.executeCommandLine(command);
		System.out.println("OS: "+OSName);
		if (OSName!=null && OSName.contains("raspberry"))
			ledExists = true;
		else 
			ledExists = false;
		init();
	}
	
	public void init(){
		if(ledExists){
			OSInteraction.executeCommandLine(init);
		} 
	}

	public void on(){
		if(ledExists){
			OSInteraction.executeCommandLine(on);
		} 
	}
	
	public void off(){
		if(ledExists){
			OSInteraction.executeCommandLine(off);
		} 
	}
	
	public void heartbeat(){
		if(ledExists){
			OSInteraction.executeCommandLine(heartbeat);
		} 
	}
	public void timer(){
		if(ledExists){
			OSInteraction.executeCommandLine(timer);
		} 
	}
	
	public static void main(String[] args){
		LedManager led = new LedManager();
		//led.heartbeat();
	}
}

