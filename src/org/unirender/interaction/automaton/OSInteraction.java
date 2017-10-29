package org.unirender.interaction.automaton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class OSInteraction {

	public static String executeCommandLine(String[] cmd){
		try {
		    Process p = Runtime.getRuntime().exec(cmd);
		    BufferedReader bri = new BufferedReader(new InputStreamReader(
		            p.getInputStream()));

		    BufferedReader bre = new BufferedReader(new InputStreamReader(
		            p.getErrorStream()));
		    
		    String line = "";
		    StringBuffer sb = new StringBuffer();
		    while ((line = bri.readLine()) != null) {
		        sb.append(line);
		    }
		    /*
		     System.out.println("Console:"+sb.toString());
		     
		    while ((line = bre.readLine()) != null) {
		        sb.append(line);
		    }
		    System.out.println("Error:"+sb.toString());
		    bre.close();
		    */
		    bri.close();
		    
		    return sb.toString().trim();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
	}
	
	public static String executeCommandLine(String cmd){
		try {
		    Process p = Runtime.getRuntime().exec(cmd);
		    BufferedReader bri = new BufferedReader(new InputStreamReader(
		            p.getInputStream()));

		    BufferedReader bre = new BufferedReader(new InputStreamReader(
		            p.getErrorStream()));
		    
		    String line = "";
		    StringBuffer sb = new StringBuffer();
		    while ((line = bri.readLine()) != null) {
		        sb.append(line);
		    }
		    System.out.println("Console:"+sb.toString());
		    while ((line = bre.readLine()) != null) {
		        sb.append(line);
		    }
		    System.out.println("Error:"+sb.toString());
		    bri.close();
		    bre.close();
		    return sb.toString().trim();
		} catch (IOException e) {
		    e.printStackTrace();
		    return null;
		}
	}
}
