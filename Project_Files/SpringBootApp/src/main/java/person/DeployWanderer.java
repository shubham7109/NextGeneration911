package person;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public class DeployWanderer {
	
	public static final double LOWERLONGITUDE = 42.008784;
	public static final double UPPERLONGITUDE = 42.062149;
	public static final double LEFTLATITUDE = -93.698529;
	public static final double RIGHTLATITUDE = -93.597377;
	
	@Autowired
	private DeployService deployService; 
	
	private String randomLat() {
		Random rand = new Random(0);
		return String.valueOf(rand.nextDouble()*(RIGHTLATITUDE - LEFTLATITUDE) + LEFTLATITUDE);
	}
	
	private String randomLong() {
		Random rand = new Random(1);
		return String.valueOf(rand.nextDouble()*(UPPERLONGITUDE - LOWERLONGITUDE) + LOWERLONGITUDE);
	}
	
	public void initializeCoord() {
		List<Deploy> deploys = deployService.getAllDeploys();
		
		for (int i = 0; i < deploys.size(); i++) {
			deployService.deleteDeploy(String.valueOf(i));
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
	
	public void wander() {
		
		
		
	}
	
}
