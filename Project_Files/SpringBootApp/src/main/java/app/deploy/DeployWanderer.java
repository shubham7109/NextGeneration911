package app.deploy;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DeployWanderer {
	
	public static final double LOWERLONGITUDE = -93.698529;
	public static final double UPPERLONGITUDE = -93.597377;
	public static final double LEFTLATITUDE = 42.008784;
	public static final double RIGHTLATITUDE = 42.062149;
	
	@Autowired
	private DeployService deployService;
	
	Random rand = new Random(System.currentTimeMillis());
	
	private String randomLat() {
		return String.valueOf(rand.nextDouble()*(RIGHTLATITUDE - LEFTLATITUDE) + LEFTLATITUDE);
	}
	
	private String randomLong() {
		return String.valueOf(rand.nextDouble()*(UPPERLONGITUDE - LOWERLONGITUDE) + LOWERLONGITUDE);
	}
	
	private double moveLat() {
		double lat = (rand.nextDouble() + 0.000001) * (RIGHTLATITUDE - LEFTLATITUDE) / 40;
		if (rand.nextInt() < 0) {
			lat = lat * -1;
		}
		return lat;
	}
	
	private double moveLong() {
		double longi = (rand.nextDouble() + 0.000001) * (UPPERLONGITUDE - LOWERLONGITUDE) / 40;
		if (rand.nextInt() < 0) {
			longi = longi * -1;
		}
		return longi;
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void initializeCoord() {
		List<Deploy> deploys = deployService.getAllDeploys();
		
		//delete each deploy in deploys
		for (int i = 0; i < deploys.size(); i++) {
			Deploy d = deploys.get(i);
			deployService.deleteDeploy(d.getID());
		}
		
		/*ambulances*/
		deploys.add(new Deploy("1", "Ambulance", "42.033162", "-93.611287"));
		deploys.add(new Deploy("2", "Ambulance", "42.033168", "-93.611287"));
		deploys.add(new Deploy("3", "Ambulance", "42.037096", "-93.583847"));
		
		/*swatTeam*/
		deploys.add(new Deploy("4", "Swat Team", randomLat(), randomLong()));
		deploys.add(new Deploy("5", "Swat Team", randomLat(), randomLong()));
		
		/*stateTroopers*/
		deploys.add(new Deploy("6", "State Trooper", randomLat(), randomLong()));
		deploys.add(new Deploy("7", "State Trooper", randomLat(), randomLong()));
		
		/*countyOfficers*/
		deploys.add(new Deploy("8", "County Officer", randomLat(), randomLong()));
		deploys.add(new Deploy("9", "county Officer", randomLat(), randomLong()));
		deploys.add(new Deploy("10", "county Officer", randomLat(), randomLong()));
		
		/*fireBrigade*/
		deploys.add(new Deploy("11", "Fire Brigade", "42.020540", "-93.36546"));
		deploys.add(new Deploy("12", "Fire Brigade", "42.020600", "-93.36546"));
		deploys.add(new Deploy("13", "Fire Brigade", "42.020640", "-93.36546"));
		deploys.add(new Deploy("14", "Fire Brigade", "42.021556", "-93.38584"));
		
		/*firstResponders*/
		deploys.add(new Deploy("15", "First Responder", randomLat(), randomLong()));
		deploys.add(new Deploy("16", "First Responder", randomLat(), randomLong()));
		deploys.add(new Deploy("17", "First Responder", randomLat(), randomLong()));
		
		for (int i = 0; i < deploys.size(); i++) {
			deployService.addDeploy(deploys.get(i));
		}	
	}
	
	@Scheduled(fixedRate = 10000)
	public void wander() {
		List<Deploy> deploy = deployService.getAllDeploys();
		
		for (int i = 0; i < deploy.size(); i++) {
			
			Deploy d = deploy.get(i);
			
			// only wander non-stationary Deploys
			if (d.getType() != "Fire Brigade" && d.getType() != "Ambulance") {
				double dLong = Double.parseDouble(d.getLongitude());
				double dLat = Double.parseDouble(d.getLatitude());
				
				dLong += moveLong();
				dLat += moveLat();
				
				//correct out of bounds wandering
				if (dLong < LOWERLONGITUDE) {
					dLong = LOWERLONGITUDE;
				}
				if (dLong > UPPERLONGITUDE) {
					dLong = UPPERLONGITUDE;
				}
				if (dLat < LEFTLATITUDE) {
					dLat = LEFTLATITUDE;
				}
				if (dLat > RIGHTLATITUDE) {
					dLat = RIGHTLATITUDE;
				}
				
				d.setLongitude(String.valueOf(dLong));
				d.setLatitude(String.valueOf(dLat));
			}	
		}
		
		for (int i = 0; i < deploy.size(); i++ ) {
			Deploy d = deploy.get(i);
			deployService.updateDeploy(d.getID(), d);
		}
		
	}
	
}
