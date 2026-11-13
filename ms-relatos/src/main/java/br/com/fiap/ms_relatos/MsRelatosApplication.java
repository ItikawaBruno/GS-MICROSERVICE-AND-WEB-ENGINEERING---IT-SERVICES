package br.com.fiap.ms_relatos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class MsRelatosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsRelatosApplication.class, args);
	}

}
