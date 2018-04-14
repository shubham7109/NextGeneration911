package person;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
		
		Thread t1 = new Thread (new Runnable() {
			
			@Override
			public void run() {
				//sleep for 15 seconds
				try {
					Thread.sleep(15000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				while (true) {
					//DeployWanderer.wander()
				}
				
			}
		});
		
		t1.start();
		
		SpringApplication.run(Main.class, args);
		
	}

}