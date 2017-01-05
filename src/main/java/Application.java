import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.ttjkst"})
@EntityScan(basePackages={"com.ttjkst.bean"}) 
@EnableJpaRepositories
@EnableWebMvc
@PropertySource({"classpath:application.properties"})
public class Application {
 
	
	
	public static void main(String[] args) {
			SpringApplication.run(Application.class, args);
	}
}
