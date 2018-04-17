package person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		
		DeployWanderer wanderer = new DeployWanderer();
		
		Thread t1 = new Thread (new Runnable() {
			
			@Override
			public void run() {
				//sleep for 15 seconds
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					wanderer.initializeCoord();
				}
				
				while (true) {
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					wanderer.wander();
				}
				
			}
		});
		
		t1.start();
		
		SpringApplication.run(Main.class, args);
		
	}

}