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
	
	public static final double LOWERLONGITUDE = 42.008784;
	public static final double UPPERLONGITUDE = 42.062149;
	public static final double LEFTLATITUDE = -93.698529;
	public static final double RIGHTLATITUDE = -93.597377;
	
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
		double lat = (rand.nextDouble() + 0.000001) * (RIGHTLATITUDE - LEFTLATITUDE) / 20;
		if (rand.nextInt() < 0) {
			lat = lat * -1;
		}
		return lat;
	}
	
	private double moveLong() {
		double longi = (rand.nextDouble() + 0.000001) * (UPPERLONGITUDE - LOWERLONGITUDE) / 20;
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
		deploys.add(new Deploy("1", "ambulance", "-93.611287", "42.033162"));
		deploys.add(new Deploy("2", "ambulance", "-93.611287", "42.033168"));
		deploys.add(new Deploy("3", "ambulance", "-93.583847", "42.037096"));
		
		/*swatTeam*/
		deploys.add(new Deploy("4", "swatTeam", randomLat(), randomLong()));
		deploys.add(new Deploy("5", "swatTeam", randomLat(), randomLong()));
		
		/*stateTroopers*/
		deploys.add(new Deploy("6", "stateTroopers", randomLat(), randomLong()));
		deploys.add(new Deploy("7", "stateTroopers", randomLat(), randomLong()));
		
		/*countyOfficers*/
		deploys.add(new Deploy("8", "countyOfficers", randomLat(), randomLong()));
		deploys.add(new Deploy("9", "countyOfficers", randomLat(), randomLong()));
		deploys.add(new Deploy("10", "countyOfficers", randomLat(), randomLong()));
		
		/*fireBrigade*/
		deploys.add(new Deploy("11", "fireBrigade", "-93.36546", "42.020540"));
		deploys.add(new Deploy("12", "fireBrigade", "-93.36546", "42.020600"));
		deploys.add(new Deploy("13", "fireBrigade", "-93.36546", "42.020640"));
		deploys.add(new Deploy("14", "fireBrigade", "-93.38584", "42.021556"));
		
		/*firstResponders*/
		deploys.add(new Deploy("15", "firstResponders", randomLat(), randomLong()));
		deploys.add(new Deploy("16", "firstResponders", randomLat(), randomLong()));
		deploys.add(new Deploy("17", "firstResponders", randomLat(), randomLong()));
		
		for (int i = 0; i < deploys.size(); i++) {
			deployService.addDeploy(deploys.get(i));
		}	
	}
	
	@Scheduled(fixedRate = 5000)
	public void wander() {
		List<Deploy> deploy = deployService.getAllDeploys();
		
		for (int i = 0; i < deploy.size(); i++) {
			
			Deploy d = deploy.get(i);
			
			// only wander non-stationary Deploys
			if (d.getType() != "fireBrigade" && d.getType() != "ambulance") {
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
