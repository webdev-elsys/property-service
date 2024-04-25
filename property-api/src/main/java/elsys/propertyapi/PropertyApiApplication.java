package elsys.propertyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
public class PropertyApiApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(PropertyApiApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "8080"));
		app.run(args);
	}
}
