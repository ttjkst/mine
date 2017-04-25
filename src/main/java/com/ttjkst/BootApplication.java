package com.ttjkst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.ttjkst.boot.config.BeforeSpringDo;
import com.ttjkst.elastic.ElasticProperties;
import com.ttjkst.elastic.ElasticUitl;
import com.ttjkst.webConfig.AllowALl;


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
	
	@Configuration
	protected class webConfig extends WebMvcConfigurerAdapter{

		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(new AllowALl());
			super.addInterceptors(registry);
		}
		
	} 
	@Configuration
	@EnableConfigurationProperties(ElasticProperties.class)
	protected static class  ElasticUitlBean {
		@Autowired
		private ElasticProperties properties;
		@Bean(destroyMethod="destory")
		ElasticUitl getElasticUitl(){
		return new ElasticUitl(properties);	
		}
	}
}
