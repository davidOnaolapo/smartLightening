package app;


import java.io.IOException;
import java.util.Random;

import nl.q42.jue.HueBridge;
import nl.q42.jue.Light;
import nl.q42.jue.StateUpdate;
import nl.q42.jue.exceptions.ApiException;

//main will act as a SERVER to receive requests to turn on/off the lights, or something fancier.

public class HueController {

	private static final String BRIDGE_IP = "192.168.0.10"; 
	private static final String USERNAME = "g2eeCw9aOuCEvvKMLGV9otgC08WdHWG0KXlNpfY5";
	private Light light;
	private HueBridge bridge;

	public HueController() throws Exception {
		bridge = new HueBridge(BRIDGE_IP);

		bridge.authenticate(USERNAME);
		
		for(Light l : bridge.getLights()) {
			if (l.getName().equals("cabinetry lighting")) {
				light = l;
			}
		}
	}
	
	public void createNewUsr() throws Exception {
		String usr = bridge.link("myapp");
		System.out.println("new usrname: " + usr);
		bridge.authenticate(usr);
	}
	public void turnLightOn() {
		Random r = new Random();
		boolean success = false;
		do {
			try {
				bridge.setLightState(light, new StateUpdate().setTransitionTime(3000).setHue(r.nextInt(65535)));
				success = true;
			} catch (IOException | ApiException e) {
			// TODO Auto-generated catch block
			}
		
		}while(!success);
		
	}
	
	public void turnLightOff() throws Exception {
		bridge.setLightState(light, new StateUpdate().turnOff());
	}
	
	public void turnLightOnFor3s() throws Exception {
		turnLightOn();
		Thread.sleep(3000);
		turnLightOff();
	}
	
	public void changeLightColor() {
		Random r = new Random();
		boolean success = false;
		do {
			try {
				bridge.setLightState(light, new StateUpdate().setTransitionTime(1000).setHue(r.nextInt(65535)));
				success = true;
			} catch (IOException | ApiException e) {
			// TODO Auto-generated catch block
			}
		
		}while(!success);
	}

	public static void main(String args[]) throws Exception {
		HueController app = new HueController();
		
		
		while (true) {
			app.changeLightColor();
			Thread.sleep(1500);
		}
		
	}
}