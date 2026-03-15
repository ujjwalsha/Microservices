package com.ecommerce.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication {

	private static final int M = 1_00_00_0007;


	static {
		System.out.println("when class load it run");
	}

	{
		System.out.println("everytime when object created");
	}

	public EurekaApplication(){
		System.out.println();
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaApplication.class, args);

		EurekaApplication eu = new EurekaApplication();
		System.out.println(M);
		System.out.println(eu);
	}

}
