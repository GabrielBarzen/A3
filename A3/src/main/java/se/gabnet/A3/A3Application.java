package se.gabnet.A3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.gabnet.A3.SOA.SOAClient;

@SpringBootApplication
public class A3Application {

	public static void main(String[] args) {
		SpringApplication.run(A3Application.class, args);


		for (int i = 1; i < 11; i++) {
			new SOAClient(i*10,10);
		}
		
		System.exit(69);
	}

}
