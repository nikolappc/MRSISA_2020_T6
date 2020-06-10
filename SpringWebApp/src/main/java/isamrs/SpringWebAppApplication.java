package isamrs;

import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@EnableJpaRepositories
public class SpringWebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebAppApplication.class, args);
		
	}

}
