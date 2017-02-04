package com.ttjkst;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ttjkst.boot.config.BeforeSpringDo;


@SpringBootApplication
@PropertySource({"classpath:application.properties"})
@ComponentScan(basePackages={"com.ttjkst"})
@EntityScan(basePackages={"com.ttjkst.bean"}) 
@EnableJpaRepositories
public class BootApplication extends SpringBootServletInitializer{
 
	
	
	public static void main(String[] args) {
			SpringApplication application = new SpringApplication(BootApplication.class);
			application.run(args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(BootApplication.class);
	}
	
	
}
