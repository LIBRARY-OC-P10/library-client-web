package org.mickael.libraryclientweb;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("org.mickael.libraryclientweb")
@EnableEurekaClient
public class LibraryClientWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryClientWebApplication.class, args);
	}

/*	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}*/

}
