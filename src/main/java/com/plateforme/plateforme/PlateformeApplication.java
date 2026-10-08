package com.plateforme.plateforme;


import com.plateforme.plateforme.auth.config.TenantProvisioningProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TenantProvisioningProperties.class)
public class PlateformeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlateformeApplication.class, args);
	}

}
