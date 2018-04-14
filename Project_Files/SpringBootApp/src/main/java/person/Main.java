package person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		
		DeployWanderer w = new DeployWanderer();
		w.initializeCoord();
		
		SpringApplication.run(Main.class, args);
	}

}