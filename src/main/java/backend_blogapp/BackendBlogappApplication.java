package backend_blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"backend_blogapp", "com.security.config"})
public class BackendBlogappApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendBlogappApplication.class, args);
	}

}
