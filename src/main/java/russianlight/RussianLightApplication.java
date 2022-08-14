package russianlight;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@SpringBootApplication
public class RussianLightApplication {

	public static void main(String[] args) {
		SpringApplication.run(RussianLightApplication.class, args);
		System.out.println("Click to view documentation: http://localhost:8080/swagger-ui/");
	}

}
