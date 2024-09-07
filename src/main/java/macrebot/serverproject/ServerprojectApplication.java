package macrebot.serverproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ServerprojectApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		System.setProperty("MONGODB_URI", dotenv.get("MONGODB_URI"));

		SpringApplication.run(ServerprojectApplication.class, args);
	}

}
